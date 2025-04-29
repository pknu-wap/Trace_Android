package com.example.domain.user

data class UserInfo(
    val name : String,
    val profileImageUrl : String? = null,
    val goodDeedScore : Int,
    val goodDeedMarkCount : Int
)
