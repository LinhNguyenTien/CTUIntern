package com.example.ctuintern.data.model

import java.io.Serializable

data class Class(
    val classID: String,
    val classCode: String,
    val className: String,
    val major: Major
): Serializable