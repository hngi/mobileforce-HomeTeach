package com.mobileforce.hometeach.models

data class Message(
    var message: String,
    var sender: Boolean,
    var createdAt: Long
)