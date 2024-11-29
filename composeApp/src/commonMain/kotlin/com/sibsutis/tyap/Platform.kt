package com.sibsutis.tyap

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform