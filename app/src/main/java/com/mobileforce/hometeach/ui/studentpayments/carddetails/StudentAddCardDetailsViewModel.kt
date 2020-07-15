package com.mobileforce.hometeach.ui.studentpayments.carddetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import kotlinx.coroutines.launch

class StudentAddCardDetailsViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun saveUserCardDetails(params: Params.CardDetails){
        viewModelScope.launch {
            try {
                userRepository.saveUserCardDetails(params)
            } catch (e: Throwable){
                e.printStackTrace()
            }
        }
    }
}