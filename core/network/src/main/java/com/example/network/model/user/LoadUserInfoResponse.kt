package com.example.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class LoadUserInfoResponse(
    val nickname : String,
    val profileImageUrl : String? = null,
    val goodDeedScore : Int = 0,
    val goodDeedMarkCount : Int = 0,
)
