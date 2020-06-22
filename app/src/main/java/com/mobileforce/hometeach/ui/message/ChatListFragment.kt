package com.mobileforce.hometeach.ui.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.models.Chat
import com.mobileforce.hometeach.models.chatDiffUtil
import com.mobileforce.hometeach.adapters.RecyclerViewAdapter
import com.mobileforce.hometeach.adapters.ViewHolder
import kotlinx.android.synthetic.main.fragment_chat_list.*

/**
 * Authored by enyason
 */

class ChatListFragment :
    Fragment(R.layout.fragment_chat_list)/* the constructor takes the layout which will be inflated at onCreateView*/ {


    private val chatListItemClickListener: (String) -> Unit = { chatId ->

        //navigate to message screen
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




    }


}