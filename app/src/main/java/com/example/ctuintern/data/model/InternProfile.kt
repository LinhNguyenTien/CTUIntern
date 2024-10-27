package com.example.ctuintern.data.model

import java.io.Serializable

data class InternProfile(
    var id: String,
    var student: Student,
    var news: News?,
    var state: InternState,
    var internReport: InternReport,
): Serializable
