package com.mobileforce.hometeach.ui.studentpayments.makepayments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import kotlinx.coroutines.launch

class StudentMakePaymentViewModel(private val userRepository: UserRepository) : ViewModel() {

    val user = userRepository.getUser()
    fun getUserCardDetails(): List<UserCardDetailResponse> {
        var cardList: List<UserCardDetailResponse> = ArrayList()
        viewModelScope.launch {
            try {
                cardList = userRepository.getUserCardDetails()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        return cardList
    }

    fun getUserDetailsFromDb(): UserEntity? {
        var userEntity: UserEntity? = null
        viewModelScope.launch {
            userEntity = userRepository.getSingleUser()
        }
        return userEntity
    }
}