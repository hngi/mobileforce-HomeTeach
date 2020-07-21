package com.mobileforce.hometeach.ui.message

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.RecyclerViewAdapter
import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.models.Chat
import com.mobileforce.hometeach.models.chatDiffUtil
import kotlinx.android.synthetic.main.fragment_chat_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * Authored by enyason
 */

class ChatListFragment :
    Fragment(R.layout.fragment_chat_list)/* the constructor takes the layout which will be inflated at onCreateView*/ {


    private val viewModel: ChatViewModel by sharedViewModel()

    private val chatListItemClickListener: (Chat) -> Unit = { chat ->

        viewModel.chatListItem = chat
        //navigate to message screen
        findNavController().navigate(R.id.action_chatListFragment_to_chatFragment)
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
        chatListRecyclerView.apply {
            adapter = chatListAdpter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.fetchUserChatList()

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            user?.let {

            }
        })

        viewModel.chatList.observe(viewLifecycleOwner, Observer {

            it?.let { list ->
                chatListAdpter.submitList(list)
            }

        })


    }


}