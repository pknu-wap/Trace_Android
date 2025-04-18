package com.example.network.model.auth

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
