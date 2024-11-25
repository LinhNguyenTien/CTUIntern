package com.example.ctuintern.data.model

data class TaskDetail(
    val taskID: String,
    val studentID: String,
    var path: String,
    val score: Double? = null
)