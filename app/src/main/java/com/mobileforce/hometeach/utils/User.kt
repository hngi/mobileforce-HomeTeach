package com.mobileforce.hometeach.utils

data class User(
    val email: String,
    val first_name: String,
    val full_name: String,
    val id: String,
    val is_active: Boolean,
    val is_admin: Boolean,
    val is_tutor: Boolean,
    val last_name: String,
    val phone_number: String,
    val timestamp: String,
    val username: String
)