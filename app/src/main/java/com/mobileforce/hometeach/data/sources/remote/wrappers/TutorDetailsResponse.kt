package com.mobileforce.hometeach.data.sources.remote.wrappers

data class TutorDetailsResponse (

    val user: User,
    val id: Int,
    val rating: Rating,
    val profile_pic: String,
    val desc: String?,
    val credentials: String?,
    val video: String?,
    val hourly_rate: String,
    val field: String?,
    val major_course: String?,
    val other_courses: String?,
    val state: String?,
    val address: String?,
    val user_url: String
)
data class User (

    val id : String,
    val email : String,
    val full_name : String,
    val username : String,
    val first_name : String,
    val last_name : String,
    val phone_number : String,
    val timestamp : String,
    val is_tutor : Boolean,
    val is_admin : Boolean,
    val is_active : Boolean
)
