package com.fadinglight

import io.ktor.server.application.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.*
import com.fadinglight.routes.registerBillRoutes


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    registerBillRoutes()
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

