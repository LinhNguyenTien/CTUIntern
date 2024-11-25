package com.example.ctuintern.data.repository

import com.example.ctuintern.data.model.InternProfile
import com.example.ctuintern.data.model.Review
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.model.Task
import com.example.ctuintern.network.APIService
import javax.inject.Inject

class ClassRepository @Inject constructor(
    private val apiService: APIService
){
    suspend fun getStudentList(classID: String) = apiService.getStudentList(classID)

    suspend fun getTaskList(classID: String) = apiService.getTaskList(classID)

    suspend fun getReviewList(teacherID: String) = apiService.getReviewList(teacherID)
}