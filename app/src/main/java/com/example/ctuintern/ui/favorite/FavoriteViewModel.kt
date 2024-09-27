package com.example.ctuintern.ui.favorite

import androidx.lifecycle.viewModelScope
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.repository.NewsRepository
import com.example.ctuintern.ui.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val newsRepository: NewsRepository): MainViewModel() {

    fun getFavoriteNews(userID: String, callback:(List<News>)->Unit) {
        viewModelScope.launch {
            val resultList = newsRepository.getFavoriteNews(userID)
            callback(resultList)
        }
    }

    fun removeNewsFromFavorites(news: News, userID: String) {
        viewModelScope.launch {
            newsRepository.removeNewsFromFavorites(news, userID)
        }
    }

}