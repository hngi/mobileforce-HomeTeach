package com.mobileforce.hometeach.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobileforce.hometeach.data.sources.local.entities.ProfileEntity
import com.mobileforce.hometeach.data.sources.local.entities.UserEntity
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.models.Chat
import com.mobileforce.hometeach.models.Message
import kotlinx.coroutines.launch

class ChatViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val db = Firebase.firestore

    val user = userRepository.getUser()
    var chatListItem: Chat? = null

    private val _chatList = MutableLiveData<List<Chat>>()
    val chatList: LiveData<List<Chat>>
        get() = _chatList

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>>
        get() = _messages

    lateinit var currentUser: UserEntity
    private lateinit var profile: ProfileEntity

    init {

        viewModelScope.launch {
            currentUser = userRepository.getSingleUser()
            profile = userRepository.getSingleUserProfile()

            fetchUserChatList()
        }

    }


    fun fetchMessages() {


        db.collection("chat")
            .document(chatListItem!!.chatId)
            .collection("message")
            .orderBy("created_at")
            .addSnapshotListener { snapShot, error ->

                error?.let {
                    return@addSnapshotListener
                }
                snapShot?.let {

                    val messages = mutableListOf<Message>()
                    for (doc in snapShot.documents) {
                        doc.toObject(Message::class.java)?.let { message ->
                            messages.add(message)
                        }
                    }

                    _messages.postValue(messages)
                }

            }
    }


    fun sendMessage(message: String) {


        val messageObject = Message(message, currentUser.id)

        val chatListMap = hashMapOf<String, Any?>(
            "last_message" to message,

            "last_message_time" to FieldValue.serverTimestamp()
        )


        val lastMessageRefCurrentUser = db
            .collection("user")
            .document(currentUser.id)
            .collection("connect")
            .document(chatListItem!!.senderId)


        val lastMessageRefOtherUser = db
            .collection("user")
            .document(chatListItem!!.senderId)
            .collection("connect")
            .document(currentUser.id)


        db.collection("chat")
            .document(chatListItem!!.chatId)
            .collection("message")
            .add(messageObject)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {


                    db.runBatch { batch ->

                        batch.update(lastMessageRefCurrentUser, chatListMap)

                        batch.update(lastMessageRefOtherUser, chatListMap)

                    }

                }
            }

    }

    private fun fetchUserChatList() {

        db
            .collection("user")
            .document(currentUser.id)
            .collection("connect")
            .orderBy("last_message_time")
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
                                    .document(currentUser.id)
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