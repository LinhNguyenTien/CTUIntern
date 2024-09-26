package com.example.ctuintern.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Employer(
    userID: String = "",
    userName: String = "",
    account: String = "",
    password: String = "",
    phone: String = "",
    email: String = "",
    profilePicture: String = "",
    role: String = "",
    var employerID: String = "",
    var address: String = "",
    var websiteAddress: String = "",
    var size: String = "",
    var field: String = ""
): User(userID, userName, account, password, phone, email, profilePicture, role), Serializable {
    override fun toString(): String {
        return "Employer(employerID='$employerID', address='$address', websiteAddress='$websiteAddress', size='$size', field='$field')"
    }
}