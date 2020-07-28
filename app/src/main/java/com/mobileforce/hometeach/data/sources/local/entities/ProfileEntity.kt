package com.mobileforce.hometeach.data.sources.local.entities

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
    val rating: Float?,
    val rating_count: Int?,
    val credentials: String?,
    val videoUrl: String?,
    val students_count: Int?,
    val profile_visits: Int?
)
