package com.example.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginKakaoRequest(
    val idToken : String
)
