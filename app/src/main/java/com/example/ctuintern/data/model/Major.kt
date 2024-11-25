package com.example.ctuintern.data.model

import java.io.Serializable

data class Major(
    val majorID: String,
    val majorName: String,
    val majorRoadMapURL: String,
    val faculty: Faculty
): Serializable