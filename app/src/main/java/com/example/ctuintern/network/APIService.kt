package com.example.ctuintern.network

import com.example.ctuintern.data.model.CheckUser
import com.example.ctuintern.data.model.Employer
import com.example.ctuintern.data.model.EmployerResponse
import com.example.ctuintern.data.model.Field
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Profile
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.model.Teacher
import com.example.ctuintern.data.model.User
import com.example.ctuintern.ulti.UserRole
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {

    @GET("/news")
    suspend fun getNews(): List<News>

    @GET("/favoriteNews/{userID}")
    suspend fun getFavoriteNews(@Path("userID") userID: String): List<News>

    @POST("/login")
    suspend fun getUserInformation(@Body checkUser: CheckUser): JsonObject

    @POST("/createUser")
    suspend fun createUser(@Body employer: Employer): Response<EmployerResponse>

    @POST("/addNewsToFavorite/{userID}")
    suspend fun addNewsToFavorite(@Body news: News, @Path("userID") userID: String)

    @POST("/removeNewsFromFavorites/{userID}")
    suspend fun removeNewsFromFavorites(@Body news: News, @Path("userID") userID: String)

    @POST("/applyNews/{userID}")
    suspend fun applyNews(@Body news: News, @Path("userID") userID: String)

    @PATCH("/update/CV/{userID}")
    suspend fun updateCV(@Body profile: Profile, @Path("userID") userID: String)
}