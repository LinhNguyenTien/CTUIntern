package com.example.ctuintern.ui.classManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.Class
import com.example.ctuintern.data.repository.UserRepository
import com.example.ctuintern.ui.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassManagementViewModel @Inject constructor(
    private val userRepository: UserRepository
): MainViewModel() {
    private val _classes: MutableLiveData<List<Class>> = MutableLiveData(listOf())
    val classes: MutableLiveData<List<Class>> get() = _classes

    fun initView(teacherID: String) {
        getClasses(teacherID)
    }

    fun getClasses(teacherID: String) {
        viewModelScope.launch {
            _classes.value = userRepository.getClasses(teacherID)
        }
    }
}