package com.example.network.model.post

import com.example.domain.model.post.WritePostType
import kotlinx.serialization.Serializable

@Serializable
data class AddPostRequest(
    val postType: String,
    val title : String,
    val content : String
)
