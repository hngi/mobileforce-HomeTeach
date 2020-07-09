package com.mobileforce.hometeach.remotesource.wrappers


class LoginResponse(
    val token: String,
    val userRemote: UserRemote
)

data class UserRemote(
    val id: String,
    val isTutor: Boolean,
    val email: String,
    val phoneNumber: String,
    val fullName: String
)