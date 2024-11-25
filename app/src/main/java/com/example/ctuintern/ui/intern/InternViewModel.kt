package com.example.ctuintern.ui.intern

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.InternProfile
import com.example.ctuintern.data.model.ReportRequest
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.model.Task
import com.example.ctuintern.data.model.TaskDetail
import com.example.ctuintern.data.model.Teacher
import com.example.ctuintern.data.repository.TaskRepository
import com.example.ctuintern.data.repository.UserRepository
import com.example.ctuintern.ui.main.MainViewModel
import com.example.ctuintern.ulti.UploadState
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val taskRepository: TaskRepository
): MainViewModel() {
    private val _uploadState: MutableLiveData<UploadState> = MutableLiveData(UploadState.WAITING)
    val uploadState: LiveData<UploadState> get() = _uploadState

    private val _internProfile: MutableLiveData<InternProfile> = MutableLiveData()
    val internProfile: LiveData<InternProfile> get() = _internProfile

    private val _teacher: MutableLiveData<Teacher> = MutableLiveData()
    val teacher: LiveData<Teacher> get() = _teacher

    private val _submitTaskState: MutableLiveData<UploadState> = MutableLiveData(UploadState.WAITING)
    val submitTaskState: LiveData<UploadState> get() = _submitTaskState

    private val _taskDetail: MutableLiveData<TaskDetail> = MutableLiveData()
    val taskDetail: LiveData<TaskDetail> get() = _taskDetail
    fun getInternProfile(userID: String) {
        viewModelScope.launch {
            try {
                val profile = userRepository.getInternProfile(userID)
                _internProfile.postValue(profile)
            } catch (e: Exception) {
                Log.e("YourViewModel", "Error fetching intern profile", e)
                // Optionally post an error state to LiveData
            }
        }
    }

    fun getTeacher(userID: String) {
        viewModelScope.launch {
            try {
                val teacher = userRepository.getTeacher(userID)
                _teacher.postValue(teacher)
            } catch (e: Exception) {
                Log.e("YourViewModel", "Error fetching intern profile", e)
                // Optionally post an error state to LiveData
            }
        }
    }

    fun getTasks(userID: String, callback: (List<Task>) -> Unit) {
        viewModelScope.launch {
            val tasks = userRepository.getTasks(userID)
            callback(tasks)
        }
    }

    fun uploadRecordToFBS(student: Student, reportID: String, uri: Uri, callback:(String) -> Unit) {
        val storage = Firebase.storage
        val cvRef = storage.reference
        val fileRef = cvRef.child("Reports/${student.userID}/studentReport")
        var uploadTask = fileRef.putFile(uri)

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            _uploadState.value = UploadState.FAIL
        }.addOnSuccessListener {
            _uploadState.value = UploadState.SUCCESS
            val newPath = it.metadata!!.path
            callback(newPath)
            uploadRecord(reportID, newPath)
        }
    }

    fun submitTaskToFBS(userID: String, taskID: String, uri: Uri, callback:(String) -> Unit) {
        val storage = Firebase.storage
        val cvRef = storage.reference
        val fileRef = cvRef.child("Tasks/$taskID/$userID")
        var uploadTask = fileRef.putFile(uri)

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            _submitTaskState.value = UploadState.FAIL
        }.addOnSuccessListener {
            _submitTaskState.value = UploadState.SUCCESS
            val newPath = it.metadata!!.path
            callback(newPath)
            submitTask(userID, taskID, newPath)
        }
    }

    fun uploadRecord(reportID: String, path: String) {
        viewModelScope.launch {
            userRepository.uploadReport(reportID, path)
        }
    }

    fun resetUploadState() {
        _uploadState.value = UploadState.WAITING
    }

    fun submitTask(userID: String, taskID: String, path: String) {
        viewModelScope.launch {
            taskRepository.submitTask(userID, taskID, ReportRequest(path))
        }
    }

    fun getTaskDetail(userID: String, taskID: String) {
        viewModelScope.launch {
            _taskDetail.value = taskRepository.getTaskDetail(userID, taskID)
        }
    }
}