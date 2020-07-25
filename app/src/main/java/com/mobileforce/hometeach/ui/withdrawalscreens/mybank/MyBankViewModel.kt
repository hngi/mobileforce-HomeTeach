package com.mobileforce.hometeach.ui.withdrawalscreens.mybank

import androidx.lifecycle.ViewModel
import com.mobileforce.hometeach.data.repository.UserRepository

class MyBankViewModel constructor(val userRepository: UserRepository) : ViewModel() {

    val user = userRepository.getUser()
    val profofile = userRepository.profileLiveData()
    val wallet = userRepository.observeWalletData()
}