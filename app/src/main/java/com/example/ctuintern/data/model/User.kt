package com.example.ctuintern.data.model

open class User(
    var userID: String,
    var userName: String,
    var account: String,
    var password: String,
    var phone: String,
    var email: String,
    var profilePicture: String,
    var role: String
) {
    override fun toString(): String {
        return "User(userID='$userID', userName='$userName', account='$account', password='$password', phone='$phone', email='$email', profilePicture='$profilePicture', role='$role')"
    }
}