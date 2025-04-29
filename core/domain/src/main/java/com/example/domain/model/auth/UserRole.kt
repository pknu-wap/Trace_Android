package com.example.domain.model.auth

enum class UserRole {
    NONE, // 프로필 설정 전
    USER
}

data class User(
    val role: UserRole,
    val signUpToken: String = "",
    val providerId: String =  "",
)