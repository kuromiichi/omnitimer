package dev.kuromiichi.omnitimer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform