package com.mobileforce.hometeach.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.RecyclerViewAdapter
import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.databinding.ChatFragmentBinding
import com.mobileforce.hometeach.models.Message
import com.mobileforce.hometeach.models.messageDiffUtil
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
    private lateinit var binding: ChatFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChatFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchMessages()

        binding.vwMessageInput.addAttachment.setOnClickListener {
//            Toast.makeText(requireContext(), "Add Attachment", Toast.LENGTH_SHORT).show()
        }

        binding.vwMessageInput.sendMsg.setOnClickListener {
            sendMessage()
        }
        viewModel.chatListItem?.let {
            binding.ivSenderImage.loadImage(it, placeholder = R.drawable.upload_pic)
        }


        val messageAdapter = object : RecyclerViewAdapter<Message>(messageDiffUtil) {
            override fun getLayoutRes(model: Message): Int {
                return if (viewModel.currentUser.id == model.sender_id) R.layout.item_message_outgoing
                else R.layout.item_message_incoming
            }

            override fun getViewHolder(
                view: View,
                recyclerViewAdapter: RecyclerViewAdapter<Message>
            ): ViewHolder<Message> {
                return MessageViewHolder(view)
            }

        }

        binding.rvChat.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
        }

        viewModel.chatListItem?.let {
            binding.tvSenderName.text = it.senderName
        }

        viewModel.messages.observe(viewLifecycleOwner, Observer {
            messageAdapter.submitList(it)
            binding.rvChat.scrollToPosition(it.lastIndex)
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