package com.mobileforce.hometeach.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Mayokun Adeniyi on 17/07/2020.
 */

@Entity(tableName = "tutors")
data class TutorEntity(
    @PrimaryKey
    val integerId: Int,
    val id: String,
    val full_name: String,
    val profile_pic: String?,
    val description: String?,
    val tutorSubject: String?,
    val hourly_rate: String?,
    var rating: Double?
)