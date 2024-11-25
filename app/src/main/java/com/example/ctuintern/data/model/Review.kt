package com.example.ctuintern.data.model

import java.io.Serializable


data class Review(
    val reviewID: String,
    val studentID: String,
    val employer: Employer,
    val reviewDay: String,
    val content: String,
    val options: Int
): Serializable