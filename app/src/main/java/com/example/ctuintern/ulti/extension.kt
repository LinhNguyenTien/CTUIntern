package com.example.ctuintern.ulti

fun String.isValidPhoneNumber(): Boolean {
    return this.matches("^\\d{10,11}$".toRegex())
}

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    return this.matches(emailRegex.toRegex())
}

fun String.isCompanyEmail(): Boolean {
    val personalDomains = listOf("gmail.com", "yahoo.com", "outlook.com", "hotmail.com")
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