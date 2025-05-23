package com.example.domain.model.post

enum class Emotion(val label: String) {
    HeartWarming("따뜻해요"),
    Likeable("좋아요"),
    Touching("감동이에요"),
    Impressive("멋져요"),
    Grateful("고마워요")
}

data class EmotionCount(
    val heartWarmingCount: Int = 0,
    val likeableCount: Int = 0,
    val touchingCount: Int = 0,
    val impressiveCount: Int = 0,
    val gratefulCount: Int = 0
) {
    companion object {
        fun fromMap(map: Map<String, Int>): EmotionCount {
            return EmotionCount(
                heartWarmingCount = map["heartwarmingCount"] ?: 0,
                likeableCount = map["likeableCount"] ?: 0,
                touchingCount = map["touchingCount"] ?: 0,
                impressiveCount = map["impressiveCount"] ?: 0,
                gratefulCount = map["gratefulCount"] ?: 0
            )
        }
    }
}