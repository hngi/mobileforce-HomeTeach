package com.mobileforce.hometeach.models

import androidx.recyclerview.widget.DiffUtil
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Message(
    var message: String? = null,
    var sender_id: String? = null,
    @ServerTimestamp
    var created_at: Date? = null
)

val messageDiffUtil = object : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.created_at?.time == newItem.created_at?.time
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }


}
