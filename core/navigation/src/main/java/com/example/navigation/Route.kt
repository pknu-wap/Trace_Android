package com.example.navigation

import kotlinx.serialization.Serializable

sealed interface Route

@Serializable
data object LoginRoute : Route

@Serializable
data object EditProfileRoute : Route
