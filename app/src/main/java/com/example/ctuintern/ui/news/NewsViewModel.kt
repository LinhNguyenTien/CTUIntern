package com.example.ctuintern.ui.news

import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.repository.NewsRepository
import com.example.ctuintern.ui.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    MainViewModel() {

    fun getNews(callback:(List<News>) -> Unit) {
        viewModelScope.launch {
            val newsList = newsRepository.getNews()
            if(newsList != null) {
                callback(newsList)
            }
            else {
                callback(emptyList())
            }
        }
    }

    fun addNewsToFavorites(news: News, userID: String) {
        viewModelScope.launch {
            newsRepository.addNewsToFavorite(news, userID)
        }
    }
}