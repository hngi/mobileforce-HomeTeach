package com.mobileforce.hometeach.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repo.UserRepository
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.utils.Result
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _signUp = MutableLiveData<Result<Nothing>>()
    val signUp: LiveData<Result<Nothing>>
        get() = _signUp

    fun signUp(params: Params.SignUp) {
        _signUp.postValue(Result.Loading)
        viewModelScope.launch {


        }

    }
}