package com.mobileforce.hometeach.ui.studentpayments.carddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import com.mobileforce.hometeach.utils.Result
import kotlinx.coroutines.launch

class StudentCardDetailsViewModel(private val userRepository: UserRepository) : ViewModel() {

    val user = userRepository.getUser()
    private val _getUserCardDetails = MutableLiveData<Result<List<UserCardDetailResponse>>>()
    val getUserCardDetails: LiveData<Result<List<UserCardDetailResponse>>> = _getUserCardDetails

    val profile = userRepository.profileLiveData()
    val wallet = userRepository.observeWalletData()

    fun getUserCardDetails() {
        _getUserCardDetails.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.getUserCardDetails()
                _getUserCardDetails.postValue(Result.Success(response))
            } catch (error: Throwable) {
                _getUserCardDetails.postValue(Result.Error(error))
            }
        }
    }
}