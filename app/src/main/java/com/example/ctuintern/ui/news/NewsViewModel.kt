package com.example.ctuintern.ui.news

import android.media.Image
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.repository.NewsRepository
import com.example.ctuintern.ui.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    MainViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _searchResults = MutableStateFlow<List<News>>(emptyList())
    val searchResults: StateFlow<List<News>> get() = _searchResults

//    init {
//        // Observe search query changes with debounce
//        viewModelScope.launch {
//            Log.i("new fragment", "call search news")
//            searchQuery
//                .debounce(300)
//                .distinctUntilChanged()
//                .collect { query ->
//                    _searchResults.value = search(query)
//                }
//        }
//    }

    private suspend fun search(query: String): List<News> {
        delay(400) // Simulate network or database delay
        return newsRepository.searchNews(query) // Replace with actual data source
    }

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

    fun addNewsToFavorites(newsID: String, userID: String) {
        viewModelScope.launch {
            newsRepository.addNewsToFavorite(newsID, userID)
        }
    }

    fun removeNewsFromFavorites(newsID: String, userID: String) {
        viewModelScope.launch {
            newsRepository.removeNewsFromFavorites(newsID, userID)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun checkFavorite(userID: String, newsID: String, favoriteView: ImageView) {
        viewModelScope.launch {
            val res = newsRepository.isFavoriteNews(userID, newsID)
            if(res.isSuccessful) {
                if(res.code() == 201) {
                    favoriteView.setImageResource(R.drawable.heart_clicked)
                }
                else {
                    favoriteView.setImageResource(R.drawable.heart_gray)
                }
            }
            else {
                Log.i("check favorite news", "error! response is not successful")
            }
        }
    }
}