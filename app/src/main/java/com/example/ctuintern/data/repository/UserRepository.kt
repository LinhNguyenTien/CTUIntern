package com.example.ctuintern.data.repository

import com.example.ctuintern.data.model.CheckUser
import com.example.ctuintern.data.model.Employer
import com.example.ctuintern.data.model.EmployerResponse
import com.example.ctuintern.data.model.Field
import com.example.ctuintern.data.model.InternProfile
import com.example.ctuintern.data.model.Profile
import com.example.ctuintern.data.model.ReportRequest
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.model.Task
import com.example.ctuintern.data.model.Teacher
import com.example.ctuintern.data.model.User
import com.example.ctuintern.network.APIService
import com.example.ctuintern.ulti.RecordType
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.coroutineScope
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: APIService) {
    suspend fun getUserInformation(email: String, password: String): JsonObject {
        return apiService.getUserInformation(CheckUser(email, password))
    }

    suspend fun createUser(employer: Employer): Response<EmployerResponse> {
        return apiService.createUser(employer)
    }

    suspend fun updateCV(profile: Profile, userID: String) {
        apiService.updateCV(profile, userID)
    }

    suspend fun getInternProfile(userID: String): InternProfile {
        return apiService.getInternProfile(userID)
    }

    suspend fun getTasks(userID: String): List<Task> {
        return apiService.getTasks(userID)
    }

    suspend fun uploadReport(reportID: String, path: String) {
        return apiService.uploadReport(reportID, ReportRequest(path))
    }

}