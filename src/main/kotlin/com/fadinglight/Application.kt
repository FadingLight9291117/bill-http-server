package com.fadinglight

import io.ktor.server.application.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.*
import com.fadinglight.routes.registerBillRoutes
import com.fadinglight.routes.registerLabelRoutes
import kotlinx.serialization.json.Json
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    registerBillRoutes()
    registerLabelRoutes()
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json { serializersModule = IdKotlinXSerializationModule }) // !!!
    }
}

