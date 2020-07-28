package com.mobileforce.hometeach.data.sources.remote.wrappers

class UserCardDetailResponse (
    val id: Int,
    val user: String,
    val card_holder_name: String,
    val card_number: String,
    val cvv: String,
    val expiry_month: Int,
    val expiry_year: Int
)