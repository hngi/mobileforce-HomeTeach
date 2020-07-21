package com.mobileforce.hometeach.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobileforce.hometeach.data.model.ProfileEntity
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.models.Chat
import kotlinx.coroutines.launch

class ChatViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val db = Firebase.firestore

    val user = userRepository.getUser()
    var chatListItem: Chat? = null

    private val _chatList = MutableLiveData<List<Chat>>()
    val chatList: LiveData<List<Chat>>
        get() = _chatList
    private lateinit var userEntity: UserEntity
    private lateinit var profile: ProfileEntity

    init {

        viewModelScope.launch {
            userEntity = userRepository.getSingleUser()
            profile = userRepository.getSingleUserProfile()

            fetchUserChatList()
        }

    }


    private fun fetchUserChatList() {

        db
            .collection("user")
            .document(userEntity.id)
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
                        val lastTime = doc.getDate("last_message_time")
                        val image = doc.getString("image")


                        val chat = Chat(
                            chatId = connectId.toString(),
                            senderName = name.toString(),
                            senderPhoto = image,
                            lastMessage = lastMessage ?: "",
                            lastMessageTime = lastTime,
                            senderId = senderId.toString()
                        )

                        chatList.add(chat)

                        //update profile image for connect when image changes
                        profile.profile_pic?.let {

                            if (it != image) {
                                db.collection("user")
                                    .document(senderId.toString())
                                    .collection("connect")
                                    .document(userEntity.id)
                                    .update(mapOf("image" to image))
                            }

                        }


                    }

                    _chatList.postValue(chatList)

                } else {
                    println("Current data: null")
                }


            }
    }


}