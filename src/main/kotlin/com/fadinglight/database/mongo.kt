package com.fadinglight.database

import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val url = "mongodb://127.0.0.1:27017"
val database = "bill"

inline fun <reified T : Any> mongo(): CoroutineCollection<T> {
    val client = KMongo.createClient(url).coroutine
    val database = client.getDatabase(database)

    return when (T::class.simpleName) {
        "Bill" -> database.getCollection("bill")
        "Label" -> database.getCollection("label")
        else -> throw IllegalArgumentException("${T::class.simpleName} is not a legal collection.")
    }
}


fun main() {
}