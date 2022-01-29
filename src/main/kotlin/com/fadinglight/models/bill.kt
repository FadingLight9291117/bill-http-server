package com.fadinglight.models


import kotlinx.serialization.*


@Serializable
data class BillItem(
    val id: String,
    val money: String,
    val options: String,
)

val bills = mutableListOf<BillItem>()