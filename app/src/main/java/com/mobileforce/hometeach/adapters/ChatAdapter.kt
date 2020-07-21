package com.mobileforce.hometeach.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.models.Message
import com.mobileforce.hometeach.utils.convertTime

/**
 * Adapter that inflates and populates the Chat UI
 * Dummy messages were loaded just to show that it works
 * Proper Chat framework or service needs to be implemented
 **/
class ChatAdapter(messages: ArrayList<Message>, private var currentUserId: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messages: ArrayList<Message> = ArrayList()
    private val viewTypeSent: Int = 1
    private val viewTypeReceived: Int = 2

    init {
        this.messages = messages
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.sender_id == currentUserId) {
            viewTypeSent
        } else {
            viewTypeReceived
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType == viewTypeSent) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_outgoing, parent, false)
            SentMessageHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_incoming, parent, false)
            ReceivedMessageHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder.itemViewType) {
            viewTypeSent -> (holder as SentMessageHolder).bind(message)
            else -> (holder as ReceivedMessageHolder).bind(message)
        }
    }

    class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var sentMsg: TextView = itemView.findViewById(R.id.tv_message_outgoing)
        private var sentTime: TextView = itemView.findViewById(R.id.tv_message_outgoing_time)

        fun bind(message: Message) {
            sentMsg.text = message.message
            sentTime.text = message.created_at.convertTime()
        }
    }

    class ReceivedMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var rcvdMsg: TextView = itemView.findViewById(R.id.tv_message_incoming)
        private var rcvdTime: TextView = itemView.findViewById(R.id.tv_message_incoming_time)

        fun bind(message: Message) {
            rcvdMsg.text = message.message
            rcvdTime.text = message.created_at.convertTime()
        }
    }
}
