package com.mobileforce.hometeach.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repo.UserRepository
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.Params.PasswordReset
import kotlinx.coroutines.launch
import org.koin.ext.scope


class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {

    //Live data goes here
    private val _reset = MutableLiveData<Result<Nothing>>()
    val reset: LiveData<Result<Nothing>>
        get() = _reset



    fun signIn(params: Params.SignIn) {

        viewModelScope.launch {

            //set live data to loading

            val response = userRepository.login(params)

            //TODO
            // 1. create a user object from the response
            // 2 save the onject to db

//            val loggedInUser = User()

//            userRepository.saveUser()

        }
    }

    fun PasswordReset(params: Params.PasswordReset) {
        viewModelScope.launch {
            try {
                userRepository.password_reset(params)
                // do some checks here first before posting anything
                //_reset.postValue("SUCCESS")
            } catch (error: Throwable) {
               // _reset.postValue(Result.Error(error))

            }
        }
    }

}