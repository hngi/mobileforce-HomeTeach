package com.mobileforce.hometeach.models

data class Request(
    val accepted: Boolean,
    val classes_request_id: String,
    val date_requested: String,
    val day: String,
    val declined: Boolean,
    val from_hour: String,
    val from_minute: String,
    val grade: String,
    val request_id: String,
    val student_id: String,
    val student_name: String,
    val student_pic: String,
    val subject: String,
    val to_hour: String,
    val to_minute: String
)