package com.mobileforce.hometeach.data.model

data class User(
    val id: String,
    val isTutor: Boolean,
    val email: String,
    val phoneNumber: String,
    val fullName: String,
    val token: String,
    val isActive:Boolean
)