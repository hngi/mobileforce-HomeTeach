package com.mobileforce.hometeach.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.ChatAdapter
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.models.Message
import com.mobileforce.hometeach.ui.BottomNavigationActivity
import com.mobileforce.hometeach.utils.toast
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.view_message_input.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*
import kotlin.collections.ArrayList

/**
 * Fragment that implements the Chat UI
 **/
class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private val viewModel: ChatViewModel by sharedViewModel()
    private lateinit var recyclerView: RecyclerView

    private val db = Firebase.firestore

    private lateinit var currentUser: UserEntity

    @ServerTimestamp
    private val lastTimestamp: Date? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as BottomNavigationActivity).hideBottomBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as BottomNavigationActivity).makeBottomBarVisible()
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
            sendMessage()
        }

        recyclerView = view.findViewById(R.id.rv_chat)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.chatListItem?.let {
            senderName.text = it.senderName

            db.collection("chat")
                .document(viewModel.chatListItem!!.chatId)
                .collection("message")
                .orderBy("created_at")
                .addSnapshotListener { snapShot, error ->

                    error?.let {
                        return@addSnapshotListener
                    }
                    snapShot?.let {

                        val messages = ArrayList<Message>()
                        for (doc in snapShot.documents) {

                            val message = doc.toObject(Message::class.java)
                            messages.add(message!!)

                        }

                        val adapter = ChatAdapter(messages, currentUser.id)
                        recyclerView.adapter = adapter
                    }

                }
        }


        viewModel.user.observe(viewLifecycleOwner, androidx.lifecycle.Observer { user ->

            user?.let {
                currentUser = it

            }
        })

    }

    private fun sendMessage() {

        val message = message_input.text.toString()

        if (message.isEmpty()) {

            toast("Message body is empty")
            return
        }

        val messageObject = Message(message, currentUser.id)

        val chatListMap =
            hashMapOf<String, Any?>("last_message" to message, "last_message_time" to lastTimestamp)


        val lastMessageRefCurrentUser = db
            .collection("user")
            .document(currentUser.id)
            .collection("connect")
            .document(viewModel.chatListItem!!.senderId)


        val lastMessageRefOtherUser = db
            .collection("user")
            .document(viewModel.chatListItem!!.senderId)
            .collection("connect")
            .document(currentUser.id)


        db.collection("chat")
            .document(viewModel.chatListItem!!.chatId)
            .collection("message")
            .add(messageObject)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    toast("message sent")
                    message_input.setText("")

                    db.runBatch { batch ->

                        batch.update(lastMessageRefCurrentUser, chatListMap)

                        batch.update(lastMessageRefOtherUser, chatListMap)

                        batch.commit()

                    }
                }
            }

    }

    // Loads dummy data to test the chat recyclerview
//    private fun loadMessages() {
//        val message1 = Message(
//            "Determine whether it is a sent message or a received message",
//            User(1, "Sola"),
//            1001
//        )
//        val message2 = Message("Obtain a message from MessageList", User(2, "Bola"), 1002)
//        val message3 =
//            Message("Inflate the appropriate layout for the ViewHolder", User(3, "Bola"), 1003)
//        val message4 =
//            Message("Pass the message to the ViewHolder for binding", User(4, "Bola"), 1004)
//        val message5 =
//            Message("Implementing this process is standard for most types", User(5, "Bola"), 1005)
//        val message6 = Message(
//            "Therefore, we’ll show the code for the full adapter without further explanation",
//            User(6, "Bola"),
//            1006
//        )
//        val message7 = Message(
//            "We’ll write these to be private inner classes of MessageListAdapter.",
//            User(7, "Bola"),
//            1007
//        )
//        val message8 = Message(
//            "Each ViewHolder holds member views that can be bound to particular information",
//            User(8, "Bola"),
//            1008
//        )
//        val message9 = Message(
//            "For example, TextView messageText binds to a message’s content",
//            User(9, "Bola"),
//            1009
//        )
//        val message10 = Message(
//            "This gives view binding to the ViewHolder class rather than to onBindViewHolder",
//            User(10, "Bola"),
//            1010
//        )
//        messages.add(message1)
//        messages.add(message2)
//        messages.add(message3)
//        messages.add(message4)
//        messages.add(message5)
//        messages.add(message6)
//        messages.add(message7)
//        messages.add(message8)
//        messages.add(message9)
//        messages.add(message10)
//    }

}