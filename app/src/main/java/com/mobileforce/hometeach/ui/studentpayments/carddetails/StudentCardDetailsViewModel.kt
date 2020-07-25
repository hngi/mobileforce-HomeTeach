package com.mobileforce.hometeach.ui.studentpayments.carddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
<<<<<<< HEAD
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserCardDetailResponse
=======
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
>>>>>>> 571221e391ceedd0b127e32b52e5d73a9e805efd
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