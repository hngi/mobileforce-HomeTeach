package com.mobileforce.hometeach.data.sources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_card")
class CardEntity(
    @PrimaryKey val id: Int,
    val user: String,
    val card_holder_name: String,
    val card_number: String,
    val cvv: String,
    val expiry_month: Int,
    val expiry_year: Int
)