package com.fadinglight.models


import kotlinx.serialization.*
import org.litote.kmongo.Id
import org.litote.kmongo.newId



@Serializable
data class Bill(
    @Contextual val _id: Id<Bill> = newId(),
    val date: String,
    val contents: MutableList<BillItem>,
)

@Serializable
data class BillItem(
    @Contextual val _id: Id<BillItem> = newId(),
    val money: String,
    val labels: MutableList<String>,
    val options: String = "",
    val flag: String = "-",
    var removed: Boolean = false,
)

fun main() {
    val id = newId<BillItem>()
    println(id)
    println(id.toString())
    val s1 = "123"
    val s2 = "1" + "23"
    println(s1 == s2)

}
