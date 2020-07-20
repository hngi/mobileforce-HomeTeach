package com.mobileforce.hometeach.models

import com.google.firebase.firestore.ServerTimestamp

data class Message(
    var message: String,
    var sender: Boolean,
    @ServerTimestamp
    var createdAt: Long
)