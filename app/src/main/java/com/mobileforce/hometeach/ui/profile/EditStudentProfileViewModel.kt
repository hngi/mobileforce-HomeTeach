package com.mobileforce.hometeach.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.local.entities.ProfileEntity
import com.mobileforce.hometeach.data.sources.local.entities.UserEntity
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.UploadResponse
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserProfileResponse
import com.mobileforce.hometeach.utils.Result
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream

/**
 * Created by MayorJay
 */
class EditStudentProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    val profile: LiveData<ProfileEntity> = userRepository.profileLiveData()
    val user: LiveData<UserEntity> = userRepository.getUser()

    private val _updateStudentProfile = MutableLiveData<Result<UserProfileResponse>>()
    val updateStudentProfile: LiveData<Result<UserProfileResponse>> = _updateStudentProfile

    private val _uploadStudentPhoto = MutableLiveData<Result<UploadResponse>>()
    val uploadStudentPhoto: LiveData<Result<UploadResponse>> = _uploadStudentPhoto

    fun updateStudentProfile(params: Params.UpdateStudentProfile) {
        _updateStudentProfile.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val userId = userRepository.getSingleUserProfile().id
                val response = userRepository.updateStudentProfile(userId, params)
                _updateStudentProfile.postValue(Result.Success(response))
            } catch (error: Throwable) {
                _updateStudentProfile.postValue(Result.Error(error))
            }
        }
    }

    fun uploadStudentPhoto(profilePic: InputStream) {
        _uploadStudentPhoto.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val userId = userRepository.getSingleUserProfile().id
                val image = MultipartBody.Part.createFormData("profile_pic", "my_pic.jpg",
                    RequestBody.create(MediaType.parse("image/*"), profilePic.readBytes()))
                val response = userRepository.uploadStudentProfilePic(userId, image)
                _uploadStudentPhoto.postValue(Result.Success(response))
            } catch (error: Throwable) {
                _uploadStudentPhoto.postValue(Result.Error(error))
            }
        }
    }

}