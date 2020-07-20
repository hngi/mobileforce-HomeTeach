package com.mobileforce.hometeach.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.ProfileResponse
import com.mobileforce.hometeach.utils.PreferenceHelper
import com.mobileforce.hometeach.utils.Result
import kotlinx.coroutines.launch

class EditTutorProfileViewModel(
    private val userRepository: UserRepository,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {

    private val _editTutorProfile = MutableLiveData<Result<Nothing>>()
    val editTutorProfile: LiveData<Result<Nothing>>
        get() = _editTutorProfile

    fun editTutorProfile(id: Int, params: Params.EditTutorProfile) {
        _editTutorProfile.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                userRepository.editTutorProfile(id, params)
                _editTutorProfile.postValue(Result.Success())
            } catch (error: Throwable) {
                _editTutorProfile.postValue(Result.Error(error))
            }
        }
    }

    fun save() {
        viewModelScope.launch {
            userRepository.save()
            preferenceHelper.isLoggedIn = false
        }
    }

    fun getProfileList(): List<ProfileResponse> {
        var profileList: List<ProfileResponse> = ArrayList()
        viewModelScope.launch {
            try {
                profileList = userRepository.getProfileList()
            } catch (error: Throwable) {
                _editTutorProfile.postValue(Result.Error(error))
            }
        }
        return profileList
    }
}