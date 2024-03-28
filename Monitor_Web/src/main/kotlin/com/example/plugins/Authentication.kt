package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureAuthentication() {
    install(Authentication) {
        firebase {
            validate {
                if (it.isEmailVerified) {
                    FirebasePrincipal(it)
                } else {
                    null
                }
            }
        }
    }
}