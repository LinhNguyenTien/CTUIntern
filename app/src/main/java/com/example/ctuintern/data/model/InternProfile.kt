package com.example.ctuintern.data.model

import java.io.Serializable

data class InternProfile(
    var internID: String,
    var student: Student,
    var news: News?,
    var internReport: InternReport,
): Serializable
