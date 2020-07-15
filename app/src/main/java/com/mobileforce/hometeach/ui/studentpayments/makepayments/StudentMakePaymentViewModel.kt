package com.mobileforce.hometeach.ui.studentpayments.makepayments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repo.UserRepository
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import kotlinx.coroutines.launch

class StudentMakePaymentViewModel(private val repository: UserRepository) : ViewModel() {

    fun getUserCardDetails(id: Int): List<UserCardDetailResponse>{
        var cardList: List<UserCardDetailResponse> = ArrayList()
        viewModelScope.launch {
            try {
                cardList = repository.getUserCardDetails(id)
            } catch (e: Throwable){
                e.printStackTrace()
            }
        }
        return cardList
    }
}