package com.mobileforce.hometeach.remotesource.wrappers

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    val token: String,
    val userRemote: UserRemote
)

data class UserRemote(
    val id: String,
    @SerializedName("is_tutor")
    val isTutor: Boolean,
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("full_name")
    val fullName: String,
    val token: String
)