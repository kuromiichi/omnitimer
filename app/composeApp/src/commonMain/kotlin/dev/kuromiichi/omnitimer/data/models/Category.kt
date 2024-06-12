package dev.kuromiichi.omnitimer.data.models

import kotlinx.serialization.Serializable

@Serializable
enum class Category(val displayName: String) {
    TWO("2x2"),
    THREE("3x3"),
    FOUR("4x4"),
    FIVE("5x5"),
    SIX("6x6"),
    SEVEN("7x7"),
    PYRA("Pyraminx"),
    SQ1("Square-1"),
    MEGA("Megaminx"),
    CLOCK("Clock"),
    SKEWB("Skewb")
}
