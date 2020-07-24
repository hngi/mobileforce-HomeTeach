package com.mobileforce.hometeach.data.sources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "wallet_info")
data class WalletEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val total_balance: Double,
    val availableBalance: Double
)