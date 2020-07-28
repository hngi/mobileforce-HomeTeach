package com.mobileforce.hometeach.ui.navdrawer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.TutorRepository
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserProfileResponse
import com.mobileforce.hometeach.utils.PreferenceHelper
import com.mobileforce.hometeach.utils.asLiveData
import kotlinx.coroutines.launch

/**
 * Created by Mayokun Adeniyi on 28/07/2020.
 */

class NavDrawerViewModel(
    private val userRepository: UserRepository,
    private val tutorRepository: TutorRepository,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {

    val user = userRepository.getUser()

    private val _tutor = MutableLiveData<UserProfileResponse>()
    val tutor = _tutor.asLiveData()


    fun logoutTutor(){
        viewModelScope.launch {

        }
    }

    fun logoutStudent(){
        viewModelScope.launch {
            userRepository.logOut()
            preferenceHelper.isLoggedIn = false
        }
    }

    fun tutorDetails(){
        viewModelScope.launch {
            val tutor = tutorRepository.getTutorDetails()
            _tutor.postValue(tutor)
        }
    }
}