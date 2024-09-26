package com.example.ctuintern.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.Employer
import com.example.ctuintern.data.model.Field
import com.example.ctuintern.data.repository.UserRepository
import com.example.ctuintern.ui.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RegisterViewModel @Inject constructor(val userRepository: UserRepository): MainViewModel() {
    fun createUser(employer: Employer, successBehavior:()->Unit, failBehavior:(String)->Unit) {
         viewModelScope.launch(Dispatchers.IO) {
             val response = userRepository.createUser(employer)
             if(response.isSuccessful) {
                 // Create employer successfully
                 successBehavior()
             }
             else {
                 // Fail cause by reason
                 val reason = response.body()!!.message
                 failBehavior(reason)
             }
         }
    }

}