package com.mobileforce.hometeach.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.utils.PreferenceHelper
import kotlinx.coroutines.launch

class HomePageViewModel(private val userRepository: UserRepository, private val preferenceHelper: PreferenceHelper) : ViewModel() {

    val user = userRepository.getUser()
    val profile = userRepository.profileLiveData()

    fun logOut(){
        viewModelScope.launch {
            userRepository.logOut()
            preferenceHelper.isLoggedIn = false
        }
    }
}