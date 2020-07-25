package com.mobileforce.hometeach.ui.studentpayments.carddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.local.entities.CardEntity
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserCardDetailResponse
import com.mobileforce.hometeach.utils.Result
import kotlinx.coroutines.launch

class StudentCardDetailsViewModel(private val userRepository: UserRepository) : ViewModel() {

    val user = userRepository.getUser()
    private val _getUserCardDetails = MutableLiveData<Result<List<UserCardDetailResponse>>>()
    val getUserCardDetails: LiveData<Result<List<UserCardDetailResponse>>> = _getUserCardDetails

    val profile = userRepository.profileLiveData()
    val wallet = userRepository.observeWalletData()
    val cards = userRepository.observeUSerCards()

    fun getUserCardDetails() {
        _getUserCardDetails.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.getUserCardDetails()
                response.forEach {

                    val card = CardEntity(
                        id = it.id,
                        user = it.user,
                        card_holder_name = it.card_holder_name,
                        card_number = it.card_number,
                        cvv = it.cvv,
                        expiry_month = it.expiry_month,
                        expiry_year = it.expiry_year
                    )

                    userRepository.saveCardToDb(card)
                }

                _getUserCardDetails.postValue(Result.Success(response))

            } catch (error: Throwable) {
                _getUserCardDetails.postValue(Result.Error(error))
            }
        }
    }
}