package com.fadinglight.database

import com.fadinglight.models.BillItem
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val client = KMongo.createClient().coroutine
val database = client.getDatabase("bill")
val col = database.getCollection<BillItem>("bill")
fun main() {
    
}