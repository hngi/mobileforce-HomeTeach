package com.mobileforce.hometeach.data.sources.remote.wrappers

data class UserProfileResponse(
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
    val credentials: String?,
    val videoUrl: String?,
    val students: Int?,
    val profile_visits: Int?
)
