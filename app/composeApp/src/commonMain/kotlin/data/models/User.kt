package data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val id: String,
    val email: String,
    val wcaId: String
)
