package com.example.domain.repository

interface SearchRepository {
    suspend fun getRecentKeywords(): Result<List<String>>
    suspend fun addKeyword(keyword : String) : Result<Unit>
    suspend fun removeKeyword(keyword: String) : Result<Unit>
    suspend fun clearKeywords() : Result<Unit>
}