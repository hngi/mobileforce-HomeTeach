package com.mobileforce.hometeach.data.sources.local.entities

import androidx.room.Entity

@Entity(tableName = "wallet_info")
data class WalletEntity(
    val total_balance: Double,
    val availableBalance: Double
)