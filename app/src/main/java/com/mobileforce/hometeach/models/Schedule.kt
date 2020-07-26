package com.mobileforce.hometeach.models

data class Schedule(
    val schedule_id: String,
    val subject: String,
    val day: String,
    val from_minute: String,
    val to_minute: String,
    val from_hour: String,
    val to_hour: String,
    val grade: String,
    val student_name: String,
    val student_id: String,
    val student_pic: String
)