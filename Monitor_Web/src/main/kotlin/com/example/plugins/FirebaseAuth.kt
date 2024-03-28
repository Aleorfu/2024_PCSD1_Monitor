package com.example.plugins

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class FirebasePrincipal(val token: FirebaseToken): Principal

class UnauthorizedUserException(message: String? = null): Exception(message)

class FirebaseAuthConfig(name: String?): AuthenticationProvider.Config(name) {
    var firebaseAuthenticationFunction: AuthenticationFunction<FirebaseToken> = {
        val message = "Firebase auth validate function is not specified, use firebase {validate { ... } } to fix this"

        throw NotImplementedError(message)
    }

    fun validate(validate: suspend ApplicationCall.(FirebaseToken) -> FirebasePrincipal?) {

        firebaseAuthenticationFunction = validate
    }
}

class FirebaseAuthProvider(config: FirebaseAuthConfig): AuthenticationProvider(config) {
    private val authFunction = config.firebaseAuthenticationFunction

    override suspend fun onAuthenticate(context: AuthenticationContext) {
        try {
            val header = context.call.request.parseAuthorizationHeader() ?: throw UnauthorizedUserException()
            val token = verifyFirebaseIDToken(header) ?: throw UnauthorizedUserException()
            val principal = authFunction(context.call, token) ?: throw UnauthorizedUserException()

            context.principal(principal)
        } catch (e: Exception) {
            context.call.respond(HttpStatusCode.Unauthorized)
        }
    }

    private suspend fun verifyFirebaseIDToken(header: HttpAuthHeader): FirebaseToken? {
        return if (header.authScheme == "Bearer" && header is HttpAuthHeader.Single) {
            withContext(Dispatchers.IO) {
                FirebaseAuth.getInstance().verifyIdToken(header.blob)
            }
        } else {
            null
        }
    }
}

const val FIREBASE_AUTH = "FIREBASE_AUTH"

fun AuthenticationConfig.firebase(
    name: String? = FIREBASE_AUTH,
    configure: FirebaseAuthConfig.() -> Unit
) {
    val provider = FirebaseAuthProvider(FirebaseAuthConfig(name).apply(configure))
    register(provider)
}