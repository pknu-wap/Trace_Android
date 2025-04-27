package com.example.domain.model.post

enum class Feeling(val label: String) {
    HeartWarming("따뜻해요"),
    Likeable("좋아요"),
    Touching("감동적이에요"),
    Impressive("멋져요"),
    Grateful("고마워요")
}

data class FeelingCount(
    val heartWarmingCount: Int = 0,
    val likeableCount: Int = 0,
    val touchingCount: Int = 0,
    val impressiveCount: Int = 0,
    val gratefulCount: Int = 0
)