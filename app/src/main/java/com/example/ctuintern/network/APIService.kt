package com.example.ctuintern.network

import com.example.ctuintern.data.model.AppliedNews
import com.example.ctuintern.data.model.CheckUser
import com.example.ctuintern.data.model.Class
import com.example.ctuintern.data.model.Employer
import com.example.ctuintern.data.model.EmployerResponse
import com.example.ctuintern.data.model.InternProfile
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Profile
import com.example.ctuintern.data.model.ReportRequest
import com.example.ctuintern.data.model.Review
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.model.Task
import com.example.ctuintern.data.model.TaskDetail
import com.example.ctuintern.data.model.Teacher
import com.example.ctuintern.data.model.User
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("/news")
    suspend fun getNews(): List<News>

    @GET("/favoriteNews/{userID}")
    suspend fun getFavoriteNews(@Path("userID") userID: String): List<News>

    @POST("/login")
    suspend fun getUserInformation(@Body checkUser: CheckUser): JsonObject

    @POST("/createUser")
    suspend fun createUser(@Body employer: Employer): Response<EmployerResponse>

    @POST("/addNewsToFavorite/{userID}/{newsID}")
    suspend fun addNewsToFavorite(@Path("userID") userID: String, @Path("newsID") newsID: String)

    @POST("/removeNewsFromFavorites/{userID}/{newsID}")
    suspend fun removeNewsFromFavorites(@Path("userID") userID: String, @Path("newsID") newsID: String);

    @POST("/applyNews/{userID}/{newsID}")
    suspend fun applyNews(@Path("userID") userID: String, @Path("newsID") newsID: String)

    @POST("/isFavoriteNews/{userID}/{newsID}")
    suspend fun checkFavorite(@Path("userID") userID: String, @Path("newsID") newsID: String): Response<Unit>

    @PATCH("/update/CV/{userID}")
    suspend fun updateCV(@Body profile: Profile, @Path("userID") userID: String)

    @GET("/getApplyNews/{userID}")
    suspend fun getApplyNews(@Path("userID") userID: String): List<AppliedNews>

    @GET("/getInternProfile/{userID}")
    suspend fun getInternProfile(@Path("userID") userID: String): InternProfile

    @GET("/getTasks/{userID}")
    suspend fun getTasks(@Path("userID") userID: String): List<Task>

    @PATCH("/uploadReport/{reportID}")
    suspend fun uploadReport(@Path("reportID") reportID: String, @Body path: ReportRequest)

    @GET("/classes/{teacherID}")
    suspend fun getClasses(@Path("teacherID") teacherID: String): List<Class>

    @POST("/updateProfilePicture/{studentID}")
    suspend fun updateProfilePicture(@Path("studentID") studentID: String, @Body path: ReportRequest)

    @PATCH("/updateProfile")
    suspend fun updateProfile(@Body user: User)

    @GET("/studentList/{classID}")
    suspend fun getStudentList(@Path("classID") classID: String): List<Student>

    @GET("/taskList/{classID}")
    suspend fun getTaskList(@Path("classID") classID: String): List<Task>
  
    @POST("/search")
    suspend fun searchNews(@Query("search") search: String): List<News>

    @GET("/reviews")
    suspend fun getReviewList(@Query("teacherID") teacherID: String, @Query("classID") classID: String? = ""): List<Review>

    @GET("/profile/{userID}")
    suspend fun getProfile(@Path("userID") userID: String): Profile

    @GET("/getClass/{userID}")
    suspend fun getClass(@Path("userID") userID: String): Class

    @GET("/getTeacher/{userID}")
    suspend fun getTeacher(@Path("userID") userID: String): Teacher
    //use userID of student

    @POST("/submitTask/{userID}/{taskID}")
    suspend fun submitTask(@Path("userID") userID: String, @Path("taskID") taskID: String, @Body path: ReportRequest)

    @GET("/getTaskDetail/{userID}/{taskID}")
    suspend fun getTaskDetail(@Path("userID") userID: String, @Path("taskID") taskID: String): TaskDetail

    @POST("/isAppliedNew/{userID}/{newsID}")
    suspend fun checkAppliedNews(@Path("userID") userID: String, @Path("newsID") newsID: String): Response<Unit>
}