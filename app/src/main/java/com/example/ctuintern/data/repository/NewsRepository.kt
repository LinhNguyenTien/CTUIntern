package com.example.ctuintern.data.repository

import com.example.ctuintern.data.model.News
import com.example.ctuintern.network.APIService
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: APIService) {

    suspend fun getNews() = apiService.getNews()

    suspend fun addNewsToFavorite(news: News, userID: String) = apiService.addNewsToFavorite(news, userID)

    suspend fun removeNewsFromFavorites(news: News, userID: String) = apiService.removeNewsFromFavorites(news, userID)

    suspend fun applyNews(news: News, userID: String) = apiService.applyNews(news, userID)

    suspend fun getFavoriteNews(userID: String) = apiService.getFavoriteNews(userID)

}