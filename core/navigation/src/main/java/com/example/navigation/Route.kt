package com.example.navigation

import kotlinx.serialization.Serializable

sealed interface Route

@Serializable
data object LoginRoute : Route

@Serializable
data object EditProfileRoute : Route

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
