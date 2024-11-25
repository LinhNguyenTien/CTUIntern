package com.example.ctuintern.data.model

data class WeeklyReview(
    val week: String,
    val fromDay: String,
    val toDay: String,
    val numberOfSessions: Int,
    val content: String,
    val review: String? = null
)
