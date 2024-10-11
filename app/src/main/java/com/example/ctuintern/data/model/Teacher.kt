package com.example.ctuintern.data.model

class Teacher(
    userID: String,
    userName: String,
    account: String,
    password: String,
    phone: String,
    email: String,
    profilePicture: String,
    role: String,
    var teacherID: String,
    var faculty: Faculty
): User(userID, userName, account, password, phone, email, profilePicture, role) {
    override fun toString(): String {
        return "Teacher(teacherID='$teacherID')"
    }
}
