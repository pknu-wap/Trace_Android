package com.example.domain.model.mypage

data class UserInfo(
    val name : String,
    val profileImageUrl : String? = null,
    val GoodDeedScore : Int,
    val GoodDeedMarkCount : Int
)
