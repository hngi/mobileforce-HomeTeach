package com.mobileforce.hometeach.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.UserRepositoryImpl
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.ProfileResponse
import com.mobileforce.hometeach.utils.Result
import kotlinx.coroutines.launch

class EditTutorProfileViewModel(private val userRepositoryImpl: UserRepositoryImpl) : ViewModel() {

    private val _editTutorProfile = MutableLiveData<Result<Nothing>>()
    val editTutorProfile: LiveData<Result<Nothing>>
        get() = _editTutorProfile

    fun editTutorProfile(id: Int, params: Params.EditTutorProfile){
        _editTutorProfile.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                userRepositoryImpl.editTutorProfile(id, params)
                _editTutorProfile.postValue(Result.Success())
            } catch (error: Throwable) {
                _editTutorProfile.postValue(Result.Error(error))
            }
        }
    }

    fun getProfileList(): List<ProfileResponse>{
        var profileList: List<ProfileResponse> = ArrayList()
        viewModelScope.launch {
            try {
                profileList = userRepositoryImpl.getProfileList()
            } catch (error: Throwable){
                _editTutorProfile.postValue(Result.Error(error))
            }
        }
        return profileList
    }
}