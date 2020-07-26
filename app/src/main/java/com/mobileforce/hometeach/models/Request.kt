package com.mobileforce.hometeach.models

data class Request(
    val accepted: Boolean,
    val declined: Boolean,
    val request_id: String,
    val date_requested: String,
    val classes_request_id: String,
    val subject: String,
    val grade: String,
    val student_name: String,
    val student_id: String,
    val student_pic: String
)