package com.mobileforce.hometeach.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.models.Chat

class ChatViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val db = Firebase.firestore

    val user = userRepository.getUser()
    var chatListItem: Chat? = null

    private val _chatList = MutableLiveData<List<Chat>>()
    val chatList: LiveData<List<Chat>>
        get() = _chatList


    fun fetchUserChatList() {

        db
            .collection("user")
            .document(user.value!!.id)
            .collection("connect")
            .addSnapshotListener { snapshot, error ->

                error?.let {
                    return@addSnapshotListener
                }

                if (snapshot != null) {

                    val chatList = mutableListOf<Chat>()
                    for (doc in snapshot.documents) {

                        val name = doc.getString("full_name")
                        val senderId = doc.getString("id")
                        val connectId = doc.getString("connect_id")
                        val lastMessage = doc.getString("last_message")


                        val chat = Chat(
                            chatId = connectId.toString(),
                            senderName = name.toString(),
                            senderPhoto = "",
                            lastMessage = lastMessage.toString(),
                            lastMessageTime = "Today",
                            senderId = senderId!!
                        )

                        chatList.add(chat)

                    }

                    _chatList.postValue(chatList)

                } else {
                    println("Current data: null")
                }


            }

    }

}