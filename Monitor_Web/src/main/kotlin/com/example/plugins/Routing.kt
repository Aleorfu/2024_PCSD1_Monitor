package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*

fun Application.configureRouting() {
    routing {
        staticResources("/static", "static")
        get("/") {
            call.respondRedirect("/login")
        }
        get("/login") {
            call.respond(ThymeleafContent("login", emptyMap()))
        }
    }
}
