package com.mobileforce.hometeach.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.utils.Result
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _signUp = MutableLiveData<Result<Nothing>>()
    val signUp: LiveData<Result<Nothing>> = _signUp


    fun signUp(params: Params.SignUp) {
        _signUp.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.register(params)

                response.errors?.let {

                    it.email?.let email@{
                        _signUp.postValue(Result.Error(Throwable(it[0])))

                        return@email
                    }

                } ?: kotlin.run {
                    _signUp.postValue(Result.Success())
                }

            } catch (error: Throwable) {
                _signUp.postValue(Result.Error(error))
            }
        }

    }
}