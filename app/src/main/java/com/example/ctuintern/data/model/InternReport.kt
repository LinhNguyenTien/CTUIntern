package com.example.ctuintern.data.model

import java.io.Serializable

data class InternReport(
    val reportID: String,
    var taskListReportPath: String? = null,
    var checkListReportPath: String? = null,
    var reviewReportPath: String? = null,
    var studentReportPath: String? = null,
    var teacherReportPath: String? = null,
    val companyReviewPath: String? = null,
    val companyScore: Double? = 0.0,
    val teacherReviewPath: String? = null,
    val teacherReviewScore: Double? = 0.0
): Serializable