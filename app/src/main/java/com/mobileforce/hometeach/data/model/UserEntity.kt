package com.mobileforce.hometeach.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * authored by enyason
 */
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: String,
    val is_tutor: Boolean,
    val email: String,
    val phone_number: String,
    val full_name: String,
    val token: String
)