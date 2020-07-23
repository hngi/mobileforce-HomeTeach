package com.mobileforce.hometeach.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.model.ProfileEntity
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.wrappers.Profile
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val user: LiveData<UserEntity> = userRepository.getUser()

    val profile: LiveData<ProfileEntity> = userRepository.profileLiveData()


    fun getUserProfile() {
        viewModelScope.launch {
            try {
                val response = userRepository.getUserProfile()

                //save profile to db

                with(response) {
                    val profile = Profile(
                        this.user,
                        id,
                        profile_pic,
                        hourly_rate,
                        rating,
                        desc,
                        field,
                        major_course,
                        other_courses,
                        state,
                        address,
                        user_url,
                        credentials = credentials,
                        videoUrl = videoUrl
                    )
                    userRepository.saveUserProfile(profile)
                }

            } catch (error: Throwable) {
            }
        }

    }

}