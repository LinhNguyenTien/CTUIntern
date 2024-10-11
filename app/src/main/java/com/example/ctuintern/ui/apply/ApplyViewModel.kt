package com.example.ctuintern.ui.apply

import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.AppliedNews
import com.example.ctuintern.data.repository.NewsRepository
import com.example.ctuintern.ui.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyViewModel @Inject constructor(private val newsRepository: NewsRepository):
    MainViewModel() {
    fun getApplyNews(userID: String, callback:(List<AppliedNews>)->Unit) {
        viewModelScope.launch {
            val appliedNews = newsRepository.getApplyNews(userID)
            callback(appliedNews)
        }
    }
}