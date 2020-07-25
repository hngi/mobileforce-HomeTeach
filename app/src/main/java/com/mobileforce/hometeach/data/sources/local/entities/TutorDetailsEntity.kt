package com.mobileforce.hometeach.data.sources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Mayokun Adeniyi on 23/07/2020.
 */

@Entity(tableName = "tutor_details")
data class TutorDetailsEntity(
    @PrimaryKey val id: Int,
    val stringId : String?,
    val full_name: String?,
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
    val videoUrl: String?
)