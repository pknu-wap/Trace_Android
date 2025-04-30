package com.example.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class GetPostRequest(
   val postId : Int,
)
