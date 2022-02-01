package com.fadinglight.models

import kotlinx.serialization.*
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class Label(
    @Contextual val _id: Id<Label> = newId(),
    val mainLabels: MutableList<String>,
    val minorLabels: MutableList<String>,
)