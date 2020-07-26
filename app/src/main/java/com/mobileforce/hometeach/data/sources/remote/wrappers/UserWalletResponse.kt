package com.mobileforce.hometeach.data.sources.remote.wrappers

import com.google.gson.annotations.SerializedName

data class UserWalletResponse(
    val status: String, val data: WalletData
)

data class WalletData(
    @SerializedName("available balance")
    val available_balance: Double,
    @SerializedName("total balance")
    val total_balance: Double
)