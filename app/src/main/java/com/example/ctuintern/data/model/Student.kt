package com.example.ctuintern.data.model

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
): User(userID, userName, account, password, phone, email, profilePicture, role) {
    override fun toString(): String {
        return "Student(studentID='$studentID', studyTime='$studyTime', GPA=$GPA)"
    }
}