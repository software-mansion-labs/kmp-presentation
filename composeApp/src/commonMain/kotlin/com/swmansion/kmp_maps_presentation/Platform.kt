package com.swmansion.kmp_maps_presentation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform