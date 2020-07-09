package com.mobileforce.hometeach.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repo.UserRepository
import com.mobileforce.hometeach.remotesource.Params
import kotlinx.coroutines.launch


class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {

    //Live data goes here

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

}