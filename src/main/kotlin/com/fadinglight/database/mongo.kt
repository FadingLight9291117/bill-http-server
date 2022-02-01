package com.fadinglight.database

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


class Mongo {
    private val config = HoconApplicationConfig(ConfigFactory.load())
    val url by lazy {
        val server = config.propertyOrNull("ktor.mongo.server")?.getString() ?: "localhost"
        val port = config.propertyOrNull("ktor.mongo.port")?.getString() ?: "27017"
        val username = config.propertyOrNull("ktor.mongo.username")?.getString()
        val password = config.propertyOrNull("ktor.mongo.password")?.getString()

        if (username == null && password == null) {
            "mongodb://${server}:${port}"
        } else {
            "mongodb://${username}:${password}@${server}:${port}"
        }
    }
    val database = config.propertyOrNull("ktor.mongo.database")?.getString() ?: "bill"

    inline operator fun <reified T : Any> invoke(): CoroutineCollection<T> {
        val client = KMongo.createClient(url).coroutine
        val database = client.getDatabase(database)

        return when (T::class.simpleName) {
            "Bill" -> database.getCollection("bill")
            "Label" -> database.getCollection("label")
            else -> throw IllegalArgumentException("${T::class.simpleName} is not a legal collection.")
        }
    }
}


fun main() {
}