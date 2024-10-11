package com.example.ctuintern.data.model

data class InternReport(
    val reportID: String,
    var taskListReportPath: String? = null,
    var checkListReportPath: String? = null,
    var reviewReportPath: String? = null,
    var studentReportPath: String? = null
)