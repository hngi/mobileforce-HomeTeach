package com.mobileforce.hometeach.ui.message

import android.view.View
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.models.Message
import com.mobileforce.hometeach.utils.convertTime
import kotlinx.android.synthetic.main.item_message_incoming.view.*
import kotlinx.android.synthetic.main.item_message_outgoing.view.*


class MessageViewHolder(itemView: View) : ViewHolder<Message>(itemView) {

    override fun bind(element: Message) {

        when (itemViewType) {
            R.layout.item_message_incoming -> {
                with(itemView) {
                    tv_message_incoming.text = element.message
                    tv_message_incoming_time.text = element.created_at.convertTime()
                }
            }
            R.layout.item_message_outgoing -> {

                with(itemView) {
                    tv_message_outgoing.text = element.message
                    tv_message_outgoing_time.text = element.created_at.convertTime()
                }
            }
        }
    }


}