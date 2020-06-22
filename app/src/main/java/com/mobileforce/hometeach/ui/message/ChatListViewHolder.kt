package com.mobileforce.hometeach.ui.message

import android.view.View
import com.mobileforce.hometeach.loadImage
import com.mobileforce.hometeach.models.Chat
import com.mobileforce.hometeach.adapters.ViewHolder
import kotlinx.android.synthetic.main.chat_list_message_item.view.*

/**
 * Authored by enyason
 */

class ChatListViewHolder(view: View, private val chatListItemClickListener: (String) -> Unit) :
    ViewHolder<Chat>(view) {
    override fun bind(element: Chat) {

        with(itemView) {

            roundedUserImage.loadImage(element.senderPhoto)
            lastMessage.text = element.lastMessage
            lastMessageTime.text = element.lastMessageTime
            userName.text = element.senderName


            setOnClickListener {
                chatListItemClickListener(element.chatId)
            }
        }
    }

}