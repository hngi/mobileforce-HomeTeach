package com.mobileforce.hometeach.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.TutorRepository
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.local.entities.ProfileEntity
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.LoginResponse
import com.mobileforce.hometeach.data.sources.remote.wrappers.UploadResponse
import com.mobileforce.hometeach.utils.Result
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream

class EditTutorViewModel(
    private val tutorRepository: TutorRepository,
    private val userRepository: UserRepository
) : ViewModel() {


    val profile: LiveData<ProfileEntity> = userRepository.profileLiveData()


    private val _postTutorDetails = MutableLiveData<Result<LoginResponse>>()
    val updateTutorProfile: LiveData<Result<LoginResponse>> = _postTutorDetails


    private val _postTutorImage = MutableLiveData<Result<UploadResponse>>()
    val uploadPhoto: LiveData<Result<UploadResponse>> = _postTutorImage

    private val _postTutorVideo = MutableLiveData<Result<UploadResponse>>()
    val uploadVideo: LiveData<Result<UploadResponse>> = _postTutorVideo


    private val _postTutorCredentials = MutableLiveData<Result<UploadResponse>>()
    val uploadPdf: LiveData<Result<UploadResponse>> = _postTutorCredentials

    var profileSuccess: Boolean = false
    var imageSuccess = false
    var credentialSuccess = false
    var videoSuccess = false


    fun updateTutorProfile(params: Params.UpdateTutorProfile) {
        _postTutorDetails.postValue(Result.Loading)
        viewModelScope.launch {

            try {
                val profileEntity = tutorRepository.getProfileId()
                val id = profileEntity.id
                val response = tutorRepository.updateTutorProfile(id, params)
                _postTutorDetails.postValue(Result.Success(response))
            } catch (error: Throwable) {
                _postTutorDetails.postValue(Result.Error(error))
            }

        }
    }

    fun uploadPhoto(profile_pic: InputStream) {
        _postTutorImage.postValue(Result.Loading)
        viewModelScope.launch {
            val profileEntity = tutorRepository.getProfileId()
            val id = profileEntity.id
            val image = MultipartBody.Part.createFormData(
                "profile_pic", "myPic.jpg", RequestBody.create(
                    MediaType.parse("image/*"),
                    profile_pic.readBytes()
                )
            )
            try {
                val response = tutorRepository.uploadProfilePic(id, image)
                _postTutorImage.postValue(Result.Success(response))
            } catch (error: Throwable) {
                _postTutorImage.postValue(Result.Error(error))
            }
        }

    }


    fun uploadVideo(video: InputStream) {
        _postTutorVideo.postValue(Result.Loading)
        viewModelScope.launch {
            val profileEntity = tutorRepository.getProfileId()
            val id = profileEntity.id
            val Video = MultipartBody.Part.createFormData(
                "video", "myVideo.mp4", RequestBody.create(
                    MediaType.parse("video/*"),
                    video.readBytes()
                )
            )
            try {
                val response = tutorRepository.uploadProfilePic(id, Video)
                _postTutorVideo.postValue(Result.Success(response))
            } catch (error: Throwable) {
                _postTutorDetails.postValue(Result.Error(error))
            }
        }
    }


    fun uploadPdf(credentials: InputStream) {
        _postTutorCredentials.postValue(Result.Loading)
        viewModelScope.launch {
            val profileEntity = tutorRepository.getProfileId()
            val id = profileEntity.id
            val pdf = MultipartBody.Part.createFormData(
                "credentials", "myCredential.pdf", RequestBody.create(
                    MediaType.parse("application/pdf"),
                    credentials.readBytes()
                )
            )
            try {
                val response = tutorRepository.uploadProfilePic(id, pdf)
                _postTutorCredentials.postValue(Result.Success(response))
            } catch (error: Throwable) {
                _postTutorCredentials.postValue(Result.Error(error))
            }
        }
    }

}