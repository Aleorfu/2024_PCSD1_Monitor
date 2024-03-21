package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.thymeleaf.*

fun Application.configureThymeleaf() {
    install(Thymeleaf)
}