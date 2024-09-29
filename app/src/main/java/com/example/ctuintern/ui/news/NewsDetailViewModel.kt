package com.example.ctuintern.ui.news

import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.repository.NewsRepository
import com.example.ctuintern.ui.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NewsDetailViewModel @Inject constructor(private val newsRepository: NewsRepository): MainViewModel() {
    fun applyNews(news: News, userID: String) {
        viewModelScope.launch {
            newsRepository.applyNews(news, userID)
        }
    }
}