package com.mobileforce.hometeach.data.sources.remote.wrappers

import com.google.gson.annotations.SerializedName


data class LoginResponse(val token: String, val profile: Profile)
data class Profile(
    val user: UserRemote,
    val id: Int,
    val profile_pic: String?,
    val hourly_rate: String?,
    val rating: Rating?,
    val desc: String?,
    val field: String?,
    val major_course: String?,
    val other_courses: String?,
    val state: String?,
    val address: String?,
    val user_url: String?,
    val credentials: String?, val videoUrl: String?
)

data class Rating(val rating: Float?, val count: Int?)

data class UserRemote(
    val id: String,
    @SerializedName("is_tutor")
    val isTutor: Boolean,
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("full_name")
    val fullName: String,
    val is_active: Boolean,
    val is_admin: Boolean,
    val timestamp: String
)

data class StudentProfileResponse(
    val user: UserRemote,
    val id: Int,
    val profile_pic: String?
)