package com.example.ctuintern.data.model

import java.io.Serializable

data class Task(
    val taskID: String? = null,
    val title: String? = null,
    val content: String? = null,
    val expiredDay: String? = null,
    val teacher: Teacher? = null
): Serializable