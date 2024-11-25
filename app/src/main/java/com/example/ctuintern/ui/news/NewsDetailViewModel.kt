package com.example.ctuintern.ui.news

import android.util.Log
import android.widget.Button
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
class NewsDetailViewModel @Inject constructor(private val newsRepository: NewsRepository): MainViewModel() {
    fun applyNews(newsID: String, userID: String) {
        viewModelScope.launch {
            newsRepository.applyNews(newsID, userID)
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

    fun checkAppliedNew(userID: String, newsID: String, appliedButton: Button) {
        viewModelScope.launch {
            val res = newsRepository.isAppliedNews(userID, newsID)
            if(res.isSuccessful) {
                if(res.code() == 201) {
                    appliedButton.text = "Đã ứng tuyển"
                }
            }
            else {
                Log.i("check applied news", "error! response is not successful")
            }
        }
    }
}