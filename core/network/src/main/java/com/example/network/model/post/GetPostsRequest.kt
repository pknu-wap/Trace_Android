package com.example.network.model.post

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetPostsRequest(
    val cursorDateTime : LocalDateTime? = null,
    val cursorId : Int? = null,
    val size : Int = 20,
    val postType : String? = null,
)
