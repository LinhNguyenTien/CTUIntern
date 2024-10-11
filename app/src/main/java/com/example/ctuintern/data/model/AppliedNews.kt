package com.example.ctuintern.data.model

import java.io.Serializable

data class AppliedNews(
    val appliedNewsID: String?,
    val student: Student?,
    val news: News?,
    val room: InterviewRoom?
): Serializable