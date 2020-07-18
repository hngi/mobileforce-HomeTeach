package com.mobileforce.hometeach.data.sources.remote.wrappers

data class StudentProfileResponse (
    val user: UserRemote,
    val id:Int,
    val profile_pic: String?,
    val hourly_rate: String?,
    val rating: Rating?,
    val desc: String?,
    val field: String?,
    val major_course: Any?,
    val other_courses: Any?,
    val state: String?,
    val address: String?,
    val user_url: String?
)