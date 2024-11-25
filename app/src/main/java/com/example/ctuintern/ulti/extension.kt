package com.example.ctuintern.ulti

import android.util.Log
import com.example.ctuintern.data.model.WeeklyReview
import com.google.firebase.storage.FirebaseStorage
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.StringReader

fun String.isValidPhoneNumber(): Boolean {
    return this.matches("^\\d{10,11}$".toRegex())
}

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    return this.matches(emailRegex.toRegex())
}

fun String.isCompanyEmail(): Boolean {
    val personalDomains = listOf("yahoo.com", "outlook.com", "hotmail.com")
    val emailDomain = this.substringAfter("@")
    return !personalDomains.contains(emailDomain)
}

fun String.isValidPassword(): Boolean {
    val passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8}$"
    return this.matches(passwordRegex.toRegex())
}

fun isValidInputStep2(account: String, password: String, confirmPassword: String, phone: String, email: String): Boolean {
    return account.isNotEmpty()
            && password.isNotEmpty()
            && confirmPassword.isNotEmpty()
            && phone.isNotEmpty()
            && email.isNotEmpty()
            && password.compareTo(confirmPassword) == 0
            && password.isValidPassword()
            && phone.isValidPhoneNumber()
            && email.isValidEmail()
            && email.isCompanyEmail()
}

fun fetchFileFromFirebaseStorage(gsUrl: String, onSuccess: (String) -> Unit, onError: (Exception) -> Unit) {
    try {
        // Initialize Firebase Storage
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl(gsUrl)

        // Fetch the file as a stream
        storageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
            val fileContent = String(bytes) // Convert bytes to string
            onSuccess(fileContent)
        }.addOnFailureListener { exception ->
            onError(exception)
        }
    } catch (e: Exception) {
        onError(e)
    }
}

data class TrainerInfo(
    val name: String,
    val startDate: String,
    val endDate: String,
    val email: String,
    val phone: String
)

data class CourseInfo(
    val trainerInfo: TrainerInfo,
    val weeklyWorks: List<WeeklyReview>
)

fun parseCourseFile_PhieuGiaoViec(fileContent: String): CourseInfo? {
    try {
        val reader = BufferedReader(StringReader(fileContent))

        // Read trainer and course info
        val trainerName = reader.readLine()?.trim() ?: return null
        val courseStartDate = reader.readLine()?.trim() ?: return null
        val courseEndDate = reader.readLine()?.trim() ?: return null

        // Parse weekly work details
        val weeklyWorks = mutableListOf<WeeklyReview>()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            if (line.isNullOrBlank()) break
            val weekNumber = line!!.trim()
            val weekStartDate = reader.readLine()?.trim() ?: ""
            val weekEndDate = reader.readLine()?.trim() ?: ""
            val numberOfSessions = reader.readLine()?.trim()?.toInt() ?: 0
            val workContent = reader.readLine()?.trim() ?: ""
            weeklyWorks.add(
                WeeklyReview(
                    week = weekNumber,
                    fromDay = weekStartDate,
                    toDay = weekEndDate,
                    numberOfSessions = numberOfSessions,
                    content = workContent
                )
            )
        }

        // Read trainer contact info
        val contactName = reader.readLine()?.trim() ?: return null
        val trainerEmail = reader.readLine()?.trim() ?: return null
        val trainerPhone = reader.readLine()?.trim() ?: return null

        // Create TrainerInfo
        val trainerInfo = TrainerInfo(
            name = trainerName,
            startDate = courseStartDate,
            endDate = courseEndDate,
            email = trainerEmail,
            phone = trainerPhone
        )
        Log.i("fetch file from url", "read file success")
        return CourseInfo(trainerInfo = trainerInfo, weeklyWorks = weeklyWorks)
    } catch (e: Exception) {
        Log.i("fetch file from url", "fail to read file")
        e.printStackTrace()
        return null
    }
}

fun parseCourseFile_PhieuTheoDoi(fileContent: String): CourseInfo? {
    try {
        val reader = BufferedReader(StringReader(fileContent))

        // Read trainer and course info
        val trainerName = reader.readLine()?.trim() ?: return null
        val courseStartDate = reader.readLine()?.trim() ?: return null
        val courseEndDate = reader.readLine()?.trim() ?: return null

        // Parse weekly work details
        val weeklyWorks = mutableListOf<WeeklyReview>()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            if (line.isNullOrBlank()) break
            val weekNumber = line!!.trim()
            val weekStartDate = reader.readLine()?.trim() ?: ""
            val weekEndDate = reader.readLine()?.trim() ?: ""
            val numberOfSessions = reader.readLine()?.trim()?.toInt() ?: 0
            val workContent = reader.readLine()?.trim() ?: ""
            val review = reader.readLine()?.trim()
            weeklyWorks.add(
                WeeklyReview(
                    week = weekNumber,
                    fromDay = weekStartDate,
                    toDay = weekEndDate,
                    numberOfSessions = numberOfSessions,
                    content = workContent,
                    review = review
                )
            )
        }

        // Read trainer contact info
        val contactName = reader.readLine()?.trim() ?: return null
        val trainerEmail = reader.readLine()?.trim() ?: return null
        val trainerPhone = reader.readLine()?.trim() ?: return null

        // Create TrainerInfo
        val trainerInfo = TrainerInfo(
            name = trainerName,
            startDate = courseStartDate,
            endDate = courseEndDate,
            email = trainerEmail,
            phone = trainerPhone
        )
        Log.i("fetch file from url", "read file success")
        return CourseInfo(trainerInfo = trainerInfo, weeklyWorks = weeklyWorks)
    } catch (e: Exception) {
        Log.i("fetch file from url", "fail to read file")
        e.printStackTrace()
        return null
    }
}

data class CompanyReview(
    val fromDay: String,
    val toDay: String,
    val i1: Int,
    val i2: Int,
    val i3: Int,
    val i4: Int,
    val ii1: Int,
    val ii2: Int,
    val ii3: Int,
    val iii1: Int,
    val iii2: Int,
    val iii3: Int,
    val total: Int,
    val otherReview: String,
    val option: Int,
    val otherOption: String,
    val mentorName: String,
    val email: String,
    val phone: String
)

fun parseCourseFile_PhieuDanhGia(fileContent: String): CompanyReview? {
    try {
        val reader = BufferedReader(StringReader(fileContent))

        // Read trainer and course info
        val fromDay = reader.readLine()?.trim() ?: return null
        val toDay = reader.readLine()?.trim() ?: return null
        val i1 = reader.readLine()?.trim()?.toInt() ?: 0
        val i2 = reader.readLine()?.trim()?.toInt() ?: 0
        val i3 = reader.readLine()?.trim()?.toInt() ?: 0
        val i4 = reader.readLine()?.trim()?.toInt() ?: 0
        val ii1 = reader.readLine()?.trim()?.toInt() ?: 0
        val ii2 = reader.readLine()?.trim()?.toInt() ?: 0
        val ii3 = reader.readLine()?.trim()?.toInt() ?: 0
        val iii1 = reader.readLine()?.trim()?.toInt() ?: 0
        val iii2 = reader.readLine()?.trim()?.toInt() ?: 0
        val iii3 = reader.readLine()?.trim()?.toInt() ?: 0
        val total = reader.readLine()?.trim()?.toInt() ?: 0
        val otherReview = reader.readLine()?.trim() ?: ""
        val option = reader.readLine()?.trim()?.toInt() ?: 0
        val otherOption = reader.readLine()?.trim() ?: ""
        val mentorName = reader.readLine()?.trim() ?: ""
        val email = reader.readLine()?.trim() ?: ""
        val phone = reader.readLine()?.trim() ?: ""
        // Create TrainerInfo
        Log.i("fetch file from url", "read file success")
        return CompanyReview(
            fromDay = fromDay,
            toDay = toDay,
            i1 = i1,
            i2 = i2,
            i3 = i3,
            i4 = i4,
            ii1 = ii1,
            ii2 = ii2,
            ii3 = ii3,
            iii1 = iii1,
            iii2 = iii2,
            iii3 = iii3,
            total = total,
            otherReview = otherReview,
            option = option,
            otherOption = otherOption,
            mentorName = mentorName,
            email = email,
            phone = phone
        )
    } catch (e: Exception) {
        Log.i("fetch file from url", "fail to read file")
        e.printStackTrace()
        return null
    }
}

data class TeacherReview(
    val i1: Double,
    val i2: Double,
    val ii1: Double,
    val ii2: Double,
    val iv: Double,
    val minus: Double
)

fun parseCourseFile_DanhGiaGiaoVien(fileContent: String): TeacherReview? {
    try {
        val reader = BufferedReader(StringReader(fileContent))

        // Read trainer and course info
        val i1 = reader.readLine()?.trim()?.toDouble() ?: 0.0
        val i2 = reader.readLine()?.trim()?.toDouble() ?: 0.0
        val ii1 = reader.readLine()?.trim()?.toDouble() ?: 0.0
        val ii2 = reader.readLine()?.trim()?.toDouble() ?: 0.0
        val iv = reader.readLine()?.trim()?.toDouble() ?: 0.0
        val minus = reader.readLine()?.trim()?.toDouble() ?: 0.0
        // Create TrainerInfo
        Log.i("fetch file from url", "read file success")
        return TeacherReview(
            i1 = i1,
            i2 = i2,
            ii1 = ii1,
            ii2 = ii2,
            iv = iv,
            minus = minus
        )
    } catch (e: Exception) {
        Log.i("fetch file from url", "fail to read file")
        e.printStackTrace()
        return null
    }
}