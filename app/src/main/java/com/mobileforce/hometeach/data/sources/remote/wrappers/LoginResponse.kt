package com.mobileforce.hometeach.data.sources.remote.wrappers

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    val token: String,
    val userRemote: UserRemote
)

data class UserRemote(
    val id: Double,
    @SerializedName("is_tutor")
    val isTutor: Boolean,
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("full_name")
    val fullName: String,
    val is_active: Boolean,
    val is_admin: Boolean,
    val timestamp:String
)