package com.example.ctuintern.data.model

data class Evaluation(
    val evaID: String,
    val companyReviewPath: String? = null,
    val companyScore: Double? = null,
    val teacherReviewPath: String? = null,
    val teacherReviewScore: Double? = null
)