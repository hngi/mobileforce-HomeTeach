package com.mobileforce.hometeach.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.ChatAdapter
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.databinding.ChatFragmentBinding
import com.mobileforce.hometeach.models.Message
import com.mobileforce.hometeach.ui.BottomNavigationActivity
import com.mobileforce.hometeach.utils.loadImage
import com.mobileforce.hometeach.utils.toast
import org.koin.android.viewmodel.ext.android.sharedViewModel

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


    private lateinit var binding: ChatFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChatFragmentBinding.inflate(layoutInflater)
        return binding.root
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

        binding.vwMessageInput.addAttachment.setOnClickListener {
//            Toast.makeText(requireContext(), "Add Attachment", Toast.LENGTH_SHORT).show()
        }


        binding.vwMessageInput.sendMsg.setOnClickListener {
            sendMessage()
        }
        viewModel.chatListItem?.let {
            binding.ivSenderImage.loadImage(it)
        }

        recyclerView = view.findViewById(R.id.rv_chat)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        viewModel.chatListItem?.let {
            binding.tvSenderName.text = it.senderName

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

        val message = binding.vwMessageInput.messageInput.text.toString()

        if (message.isEmpty()) {

            toast("Message body is empty")
            return
        }
        viewModel.sendMessage(message).also {
            binding.vwMessageInput.messageInput.setText("")
        }

    }

}