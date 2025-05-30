package com.example.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UpdateNicknameRequest(
    val nickname : String
)
