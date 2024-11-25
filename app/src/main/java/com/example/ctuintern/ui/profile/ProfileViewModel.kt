package com.example.ctuintern.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.Class
import com.example.ctuintern.data.model.Profile
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

    private val _newProfilePath: MutableLiveData<String> = MutableLiveData()
    val newProfilePath: LiveData<String> get() = _newProfilePath

    private val _profile: MutableLiveData<Profile> = MutableLiveData()
    val profile: LiveData<Profile> get() = _profile

    private val _classCTU: MutableLiveData<Class> = MutableLiveData()
    val classCTU: LiveData<Class> get() = _classCTU

    fun initView(userID: String) {
        getStudentProfile(userID)
        getClassCTU(userID)
    }

    private fun getStudentProfile(userID: String) {
        viewModelScope.launch {
            _profile.value = userRepository.getProfile(userID)
        }
    }

    private fun getClassCTU(userID: String) {
        viewModelScope.launch {
            _classCTU.value = userRepository.getClass(userID)
        }
    }

    private fun updateCV(student: Student, profile: Profile) {
        viewModelScope.launch {
            userRepository.updateCV(profile, student.userID)
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

    fun uploadResourceToFBS(student: Student, profile: Profile, folder: String, uri: Uri, callback:(String) -> Unit) {
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
                profile.CVPath = newPath
                callback(newPath)
                updateCV(student, profile)
            }
            else {
                _newProfilePath.value = newPath
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