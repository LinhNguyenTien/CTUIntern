package com.example.ctuintern.data.repository

import com.example.ctuintern.data.model.Class
import com.example.ctuintern.data.model.News
import com.example.ctuintern.network.APIService
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: APIService) {

    suspend fun getNews() = apiService.getNews()

    suspend fun addNewsToFavorite(newsID: String, userID: String) = apiService.addNewsToFavorite(userID, newsID)

    suspend fun removeNewsFromFavorites(newsID: String, userID: String) = apiService.removeNewsFromFavorites(userID, newsID)

    suspend fun applyNews(newsID: String, userID: String) = apiService.applyNews(userID, newsID)

    suspend fun getFavoriteNews(userID: String) = apiService.getFavoriteNews(userID)

    suspend fun getApplyNews(userID: String) = apiService.getApplyNews(userID)

    suspend fun searchNews(query: String) = apiService.searchNews(query)

    suspend fun isFavoriteNews(userID: String, newsID: String) = apiService.checkFavorite(userID, newsID)

    suspend fun isAppliedNews(userID: String, newsID: String) = apiService.checkAppliedNews(userID, newsID)
}