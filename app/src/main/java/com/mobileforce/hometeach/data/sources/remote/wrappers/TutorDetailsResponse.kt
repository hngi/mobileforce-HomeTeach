package com.mobileforce.hometeach.data.sources.remote.wrappers

data class TutorDetailsResponse (

    val id: Int,
    val email: String,
    val profile_pic: String,
    var full_name: String,
    val desc: String,
    val field: String,
    val major_course: Any,
    val other_courses: Any,
    val state: String,
    val address: String,
    val user_url: String
)