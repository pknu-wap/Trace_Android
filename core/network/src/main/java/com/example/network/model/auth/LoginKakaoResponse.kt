package com.example.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginKakaoResponse(
    val userId : String?,
    val accessToken : String?,
    val refreshToekn : String?
)

