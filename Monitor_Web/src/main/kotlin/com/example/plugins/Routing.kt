package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*

fun Application.configureRouting() {
    routing {
        staticResources("/static", "static")
        authenticate(FIREBASE_AUTH) {

        }
        get("/") {
            call.respondRedirect("/login")
        }
        get("/login") {
            call.respond(ThymeleafContent("Login", emptyMap()))
        }
        get("/admin") {
            call.respond(ThymeleafContent("Admin", emptyMap()))
        }
        get("/newuser") {
            call.respond(ThymeleafContent("NewUser", emptyMap()))
        }
        get("/perfil") {
            call.respond(ThymeleafContent("Perfil", emptyMap()))
        }
        get("/medico") {
            call.respond(ThymeleafContent("Medico", emptyMap()))
        }
        get("/inicio") {
            call.respond(ThymeleafContent("Usuario", emptyMap()))
        }
    }

}
