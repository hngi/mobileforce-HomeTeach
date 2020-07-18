package com.mobileforce.hometeach.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int,
    val profile_pic: String?,
    val hourly_rate: String?,
    val desc: String?,
    val field: String?,
    val major_course: String?,
    val other_courses: String?,
    val state: String?,
    val address: String?,
    val user_url: String?,
    val rating: Double? = 0.0,
    val rating_count: Int? = 0
)
