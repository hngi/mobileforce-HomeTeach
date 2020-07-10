package com.mobileforce.hometeach.ui.message

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.ChatAdapter
import com.mobileforce.hometeach.models.Message
import com.mobileforce.hometeach.models.User
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Fragment that implements the Chat UI
 **/
class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var viewModel: ChatViewModel
    private var messages: ArrayList<Message> = ArrayList()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        // Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val senderImage = view.findViewById<CircleImageView>(R.id.iv_sender_image)
        val senderName = view.findViewById<TextView>(R.id.tv_sender_name)
        val messageInput = view.findViewById<EditText>(R.id.message_input)
        val addAttachment = view.findViewById<ImageButton>(R.id.add_attachment)
        val sendMsg = view.findViewById<ImageButton>(R.id.send_msg)

        addAttachment.setOnClickListener {
            Toast.makeText(requireContext(), "Add Attachment", Toast.LENGTH_SHORT).show()
        }

        sendMsg.setOnClickListener {
            Toast.makeText(requireContext(), "Send Message", Toast.LENGTH_SHORT).show()
        }

        loadMessages()
        recyclerView = view.findViewById(R.id.rv_chat)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ChatAdapter(messages)
        recyclerView.adapter = adapter
    }

    // Loads dummy data to test the chat recyclerview
    private fun loadMessages(){
        val message1 = Message("Determine whether it is a sent message or a received message", User(1, "Sola"), 1001)
        val message2 = Message("Obtain a message from MessageList", User(2, "Bola"), 1002)
        val message3 = Message("Inflate the appropriate layout for the ViewHolder", User(3, "Bola"), 1003)
        val message4 = Message("Pass the message to the ViewHolder for binding", User(4, "Bola"), 1004)
        val message5 = Message("Implementing this process is standard for most types", User(5, "Bola"), 1005)
        val message6 = Message("Therefore, we’ll show the code for the full adapter without further explanation", User(6, "Bola"), 1006)
        val message7 = Message("We’ll write these to be private inner classes of MessageListAdapter.", User(7, "Bola"), 1007)
        val message8 = Message("Each ViewHolder holds member views that can be bound to particular information", User(8, "Bola"), 1008)
        val message9 = Message("For example, TextView messageText binds to a message’s content", User(9, "Bola"), 1009)
        val message10 = Message("This gives view binding to the ViewHolder class rather than to onBindViewHolder", User(10, "Bola"), 1010)
        messages.add(message1)
        messages.add(message2)
        messages.add(message3)
        messages.add(message4)
        messages.add(message5)
        messages.add(message6)
        messages.add(message7)
        messages.add(message8)
        messages.add(message9)
        messages.add(message10)
    }

}