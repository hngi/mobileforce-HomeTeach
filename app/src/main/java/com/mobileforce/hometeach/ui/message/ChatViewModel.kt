package com.mobileforce.hometeach.ui.message

import androidx.lifecycle.ViewModel
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.models.Chat

class ChatViewModel(private val userRepository: UserRepository) : ViewModel() {

    val user = userRepository.getUser()
    var chatListItem: Chat? = null

}