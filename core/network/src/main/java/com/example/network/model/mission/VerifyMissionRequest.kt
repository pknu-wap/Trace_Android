package com.example.network.model.mission

import kotlinx.serialization.Serializable

@Serializable
data class VerifyMissionRequest(
    val title : String,
    val content : String,
)
