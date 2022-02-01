package com.fadinglight

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*


class Foo() {
    operator fun invoke(i: Int) {
        println(i)
    }
}

fun main() {
    val config = HoconApplicationConfig(ConfigFactory.load())
    println(config.propertyOrNull("ktor.mongo.port")?.getString())
}