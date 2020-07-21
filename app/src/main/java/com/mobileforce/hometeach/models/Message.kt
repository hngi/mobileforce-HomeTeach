package com.mobileforce.hometeach.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Message(
    var message: String? = null,
    var sender_id: String? = null,
    @ServerTimestamp
    var created_at: Date? = null
)