package com.example.ctuintern.data.repository

import com.example.ctuintern.data.model.ReportRequest
import com.example.ctuintern.network.APIService
import javax.inject.Inject

class TaskRepository @Inject constructor(private val apiService: APIService){
    suspend fun submitTask(userID: String, taskID: String, path: ReportRequest)
    = apiService.submitTask(userID, taskID, path)

    suspend fun getTaskDetail(userID: String, taskID: String)
    = apiService.getTaskDetail(userID, taskID)
}