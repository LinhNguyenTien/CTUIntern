package com.example.ctuintern.data.model

import java.io.Serializable

class Student(
    userID: String,
    userName: String,
    account: String,
    password: String,
    phone: String,
    email: String,
    profilePicture: String,
    role: String,
    val studentID: String,
    val studyTime: String,
    val GPA: Float
): Serializable, User(userID, userName, account, password, phone, email, profilePicture, role)