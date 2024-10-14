package com.example.ctuintern.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.Employer
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.model.Teacher
import com.example.ctuintern.data.model.User
import com.example.ctuintern.data.repository.UserRepository
import com.example.ctuintern.ui.main.MainViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository): MainViewModel() {
    fun authenticationAccount(email: String, password: String, successBehavior:(User) -> Unit, failBehavior:() -> Unit) {
        viewModelScope.launch {
            try {
                Log.i("authen","authenticationAccount")
                val jsonObject = userRepository.getUserInformation(email, password)
                delay(1000L)
                if (jsonObject != null) {
                    val role = jsonObject.get("role").asString
                    when(role) {
                        ROLE_STUDENT -> {
                            val student = Gson().fromJson(jsonObject, Student::class.java)
                            successBehavior(student)
                        }
                        ROLE_TEACHER -> {
                            val teacher = Gson().fromJson(jsonObject, Teacher::class.java)
                            successBehavior(teacher)
                        }
                        ROLE_EMPLOYER -> {
                            val employer = Gson().fromJson(jsonObject, Employer::class.java)
                            successBehavior(employer)
                        }
                        else -> {
                            throw IllegalArgumentException("Unknown role type")
                        }
                    }
                    Log.i("authen", "success behavior")
                } else {
                    Log.i("authen", "fail behavior")
                    failBehavior()
                }
            } catch (e: Exception) {
                Log.i("exception call api", "Exception: $e")
                failBehavior()
                // Handle exceptions (like network errors)
                e.printStackTrace()
            }
        }
    }

    companion object {
        // Role tag
        val ROLE_EMPLOYER = "employer"
        val ROLE_STUDENT = "student"
        val ROLE_TEACHER = "teacher"
    }
}