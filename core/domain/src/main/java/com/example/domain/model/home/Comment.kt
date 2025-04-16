package com.example.domain.model.home

import java.time.LocalDateTime

data class Comment(
    val nickName: String,
    val profileImage : String? = null,
    val content : String,
    val createdAt : LocalDateTime,
)