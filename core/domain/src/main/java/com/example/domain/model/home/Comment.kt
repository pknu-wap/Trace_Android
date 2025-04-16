package com.example.domain.model.home

import java.time.LocalDateTime

data class Comment(
    val nickName: String,
    val content : String,
    val createdAt : LocalDateTime,
    val profileImage : String? = null
)