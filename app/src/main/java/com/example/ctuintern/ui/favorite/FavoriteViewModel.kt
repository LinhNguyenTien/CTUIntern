package com.example.ctuintern.ui.favorite

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import com.example.ctuintern.R
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
            newsRepository.removeNewsFromFavorites(news.newID.toString(), userID)
        }
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