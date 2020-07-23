package com.mobileforce.hometeach.ui.studentpayments.carddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.utils.Result
import kotlinx.coroutines.launch

class StudentAddCardDetailsViewModel(private val userRepository: UserRepository) : ViewModel() {

    val user = userRepository.getUser()

    private val _saveCard = MutableLiveData<Result<Nothing>>()
    val saveCard: LiveData<Result<Nothing>>
        get() = _saveCard

    fun saveUserCardDetails(params: Params.CardDetails) {

        _saveCard.value = Result.Loading
        viewModelScope.launch {
            try {
                userRepository.saveUserCardDetails(params)
                _saveCard.postValue(Result.Success())
            } catch (e: Throwable) {
                _saveCard.postValue(Result.Error(e))
            }
        }
    }
}