package com.mobileforce.hometeach.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repo.UserRepository
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.asLiveData
import com.mobileforce.hometeach.utils.toDomain
import kotlinx.coroutines.launch


class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {

    //Live data goes here
    private val _signIn = MutableLiveData<Result<Nothing>>()
    val signIn = _signIn.asLiveData()

    fun signIn(params: Params.SignIn) {
        _signIn.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.login(params)
                userRepository.saveUser(response.userRemote.toDomain())
                _signIn.postValue(Result.Success())
            }catch (error: Throwable){
                _signIn.postValue(Result.Error(error))
            }

        }
    }

}