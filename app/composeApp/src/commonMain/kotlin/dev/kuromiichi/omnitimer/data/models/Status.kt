package dev.kuromiichi.omnitimer.data.models

import kotlinx.serialization.Serializable

@Serializable
enum class Status {
    OK, PLUS_TWO, DNF
}
