package com.mobileforce.hometeach.ui.withdrawalscreens.withdraw

import androidx.lifecycle.ViewModel
import com.mobileforce.hometeach.data.repository.UserRepository

class WithDrawalViewModel constructor(val userRepository: UserRepository) : ViewModel() {

    val user = userRepository.getUser()
    val profofile = userRepository.profileLiveData()
    val wallet = userRepository.observeWalletData()
}