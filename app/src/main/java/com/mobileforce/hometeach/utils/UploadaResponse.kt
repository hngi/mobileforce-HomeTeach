package com.mobileforce.hometeach.utils

data class UploadaResponse(
    val address: String,
    val credentials: Any,
    val desc: String,
    val `field`: String,
    val hourly_rate: String,
    val id: Int,
    val major_course: String,
    val other_courses: String,
    val profile_pic: String,
    val rating: Rating,
    val state: String,
    val user: User,
    val user_url: String,
    val video: Any
)