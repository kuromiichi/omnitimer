package dev.kuromiichi.omnitimer.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Scramble(
    val value: String,
    val image: String
)
