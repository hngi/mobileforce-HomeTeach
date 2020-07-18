package com.mobileforce.hometeach.ui.studentpayments.carddetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import kotlinx.coroutines.launch

class StudentCardDetailsViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUserCardDetails(): List<UserCardDetailResponse>{
        var cardList: List<UserCardDetailResponse> = ArrayList()
        viewModelScope.launch {
            try {
                val userId = userRepository.getSingleUser().id
                cardList = userRepository.getUserCardDetails(userId)
            } catch (e: Throwable){
                e.printStackTrace()
            }
        }
        return cardList
    }
}