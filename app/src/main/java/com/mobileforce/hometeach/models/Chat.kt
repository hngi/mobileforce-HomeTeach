package com.mobileforce.hometeach.models

import androidx.recyclerview.widget.DiffUtil

/**
 * Authored by enyason
 */

data class Chat(
    val chatId: String,
    val senderName: String,
    val senderPhoto: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val senderId: String
)

val chatDiffUtil = object : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.chatId == newItem.chatId
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }

}