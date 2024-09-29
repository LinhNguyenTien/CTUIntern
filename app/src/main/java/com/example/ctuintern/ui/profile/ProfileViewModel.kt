package com.example.ctuintern.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.repository.UserRepository
import com.example.ctuintern.ui.main.MainViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val userRepository: UserRepository): MainViewModel() {

    private val _uploadState: MutableLiveData<UploadState> = MutableLiveData(UploadState.WAITING)
    val uploadState: LiveData<UploadState> get() = _uploadState
    fun updateCV(student: Student) {
        viewModelScope.launch {
            userRepository.updateCV(student.profile, student.userID)
        }
    }

    fun uploadCVToFirebaseStorage(student: Student, uri: Uri) {
        val storage = Firebase.storage
        val cvRef = storage.reference
        val fileRef = cvRef.child("CV/${student.userID}")
        var uploadTask = fileRef.putFile(uri)

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            _uploadState.value = UploadState.FAIL
        }.addOnSuccessListener {
            _uploadState.value = UploadState.SUCCESS
            val newPath = it.metadata!!.path
            student.profile.CVPath = newPath
            updateCV(student)
        }
    }
}

enum class UploadState {
    SUCCESS,
    FAIL,
    WAITING
}