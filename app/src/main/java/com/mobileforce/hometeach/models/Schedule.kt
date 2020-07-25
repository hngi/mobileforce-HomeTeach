package com.mobileforce.hometeach.models

data class Schedule(
    val day: String,
    val from_hour: String,
    val from_minute: String,
    val grade: String,
    val schedule_id: String,
    val student_id: String,
    val student_name: String,
    val student_pic: String,
    val subject: String,
    val to_hour: String,
    val to_minute: String
)