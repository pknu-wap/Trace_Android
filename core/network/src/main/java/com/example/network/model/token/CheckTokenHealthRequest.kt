package com.example.network.model.token

import kotlinx.serialization.Serializable

@Serializable
data class CheckTokenHealthRequest(
    val token : String
)
