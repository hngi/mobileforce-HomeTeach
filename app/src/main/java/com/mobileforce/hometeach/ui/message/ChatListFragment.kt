package com.mobileforce.hometeach.ui.message

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.RecyclerViewAdapter
import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.databinding.FragmentChatListBinding
import com.mobileforce.hometeach.models.Chat
import com.mobileforce.hometeach.models.chatDiffUtil
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * Authored by enyason
 */

class ChatListFragment : Fragment() {


    private val viewModel: ChatViewModel by sharedViewModel()

    private lateinit var binding: FragmentChatListBinding

    private val chatListItemClickListener: (Chat) -> Unit = { chat ->

        viewModel.chatListItem = chat
        //navigate to message screen
        findNavController().navigate(R.id.action_chatListFragment_to_chatFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * build chat list adapter
         */
        val chatListAdpter = object : RecyclerViewAdapter<Chat>(chatDiffUtil) {
            override fun getLayoutRes(): Int {
                return R.layout.chat_list_message_item
            }

            override fun getViewHolder(
                view: View,
                recyclerViewAdapter: RecyclerViewAdapter<Chat>
            ): ViewHolder<Chat> {
                return ChatListViewHolder(view, chatListItemClickListener)
            }

        }

        //setup chat list recycler view
        binding.chatListRecyclerView.apply {
            adapter = chatListAdpter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            user?.let {

            }
        })

        viewModel.chatList.observe(viewLifecycleOwner, Observer {

            it?.let { list ->
                chatListAdpter.submitList(list)
            }

        })


        // handle in app filter
        binding.chatListSearchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (!s.isNullOrEmpty()) {

                    val filter = viewModel.chatList.value?.filter {
                        s.toString().equals(it.senderName, true)
                    }

                    filter?.let {
                        chatListAdpter.submitList(it)
                    }
                } else {

                    viewModel.chatList.value?.let {
                        chatListAdpter.submitList(it)
                    }
                }
            }

        })


    }


}