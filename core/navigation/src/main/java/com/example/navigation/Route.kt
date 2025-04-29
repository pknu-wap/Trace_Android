package com.example.navigation

import kotlinx.serialization.Serializable

sealed interface Route

@Serializable
data object LoginRoute : Route

@Serializable
data class EditProfileRoute(val signUpToken : String, val providerId: String) : Route

@Serializable
data object HomeRoute : Route

@Serializable
data object MissionRoute : Route

@Serializable
data object MyPageRoute : Route

@Serializable
data object PostRoute : Route

@Serializable
data object WritePostRoute : Route
