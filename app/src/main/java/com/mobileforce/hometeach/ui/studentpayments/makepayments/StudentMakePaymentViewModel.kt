package com.mobileforce.hometeach.ui.studentpayments.makepayments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.local.entities.CardEntity
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserCardDetailResponse
import com.mobileforce.hometeach.utils.Result
import kotlinx.coroutines.launch

class StudentMakePaymentViewModel(private val userRepository: UserRepository) : ViewModel() {

    val user = userRepository.getUser()
    private val _getUserCardDetails = MutableLiveData<Result<List<UserCardDetailResponse>>>()

    val profofile = userRepository.profileLiveData()
    val wallet = userRepository.observeWalletData()

    val cards = userRepository.observeUSerCards()


    fun getUserCardDetails() {
        _getUserCardDetails.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.getUserCardDetails()
                _getUserCardDetails.postValue(Result.Success(response))
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

            } catch (error: Throwable) {
                _getUserCardDetails.postValue(Result.Error(error))
            }
        }
    }
}