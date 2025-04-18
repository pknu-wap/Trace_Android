package com.example.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserRequest(
    val idToken: String,
    val nickname: String,
    val profileImage: String? = null
)
