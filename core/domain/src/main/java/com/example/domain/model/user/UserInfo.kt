package com.example.domain.model.user

data class UserInfo(
    val name : String,
    val profileImageUrl : String? = null,
    val goodDeedScore : Int,
    val goodDeedMarkCount : Int
)
