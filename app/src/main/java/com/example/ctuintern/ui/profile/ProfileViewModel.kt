package com.example.ctuintern.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.ReportRequest
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.repository.UserRepository
import com.example.ctuintern.ui.main.MainViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository): MainViewModel() {

    private val _uploadState: MutableLiveData<UploadState> = MutableLiveData(UploadState.WAITING)
    val uploadState: LiveData<UploadState> get() = _uploadState
    private fun updateCV(student: Student) {
        viewModelScope.launch {
            userRepository.updateCV(student.profile, student.userID)
        }
    }

    private fun updateProfilePicture(student: Student, newPath: String) {
        viewModelScope.launch {
            userRepository.updateProfilePicture(student.userID, ReportRequest(newPath))
        }
    }

    fun updateProfile(student: Student) {
        viewModelScope.launch {
            userRepository.updateProfile(student)
        }
    }

    fun uploadResourceToFBS(student: Student, folder: String, uri: Uri, callback:(String) -> Unit) {
        val storage = Firebase.storage
        val cvRef = storage.reference
        val fileRef = cvRef.child("$folder/${student.userID}/${uri.lastPathSegment}")
        var uploadTask = fileRef.putFile(uri)

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            _uploadState.value = UploadState.FAIL
        }.addOnSuccessListener {
            _uploadState.value = UploadState.SUCCESS
            val newPath = it.metadata!!.path
            if(folder == "CV") {
                student.profile.CVPath = newPath
                callback(newPath)
                updateCV(student)
            }
            else {
                updateProfilePicture(student, newPath)
            }
        }
    }

    fun resetUploadState() {
        _uploadState.value = UploadState.WAITING
    }
}

enum class UploadState {
    SUCCESS,
    FAIL,
    WAITING
}