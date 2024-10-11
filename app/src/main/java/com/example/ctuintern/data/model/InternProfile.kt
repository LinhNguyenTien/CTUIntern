package com.example.ctuintern.data.model

data class InternProfile(
    var id: String,
    var student: Student,
    var news: News,
    var state: InternState,
    var internReport: InternReport,
    var evaluation: Evaluation
)
