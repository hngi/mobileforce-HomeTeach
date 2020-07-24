package com.mobileforce.hometeach.data.repository

import com.mobileforce.hometeach.data.model.ProfileEntity
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.LoginResponse
import com.mobileforce.hometeach.data.sources.remote.wrappers.UploadResponse
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserProfileResponse
import com.mobileforce.hometeach.models.TutorRequestDataModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface TutorRepository {
    suspend fun getTutorDetails(): UserProfileResponse

    suspend fun uploadTutorMedia(id: RequestBody,
                                 profile_pic: MultipartBody.Part,
                                 credentials: MultipartBody.Part,
                                 video: MultipartBody.Part): Response<UploadResponse>


    suspend fun updateTutorProfile(id:Int,params:Params.UpdateTutorProfile):LoginResponse

    suspend fun getProfileId(): ProfileEntity
    suspend fun uploadProfilePic(
        id: Int,
        profile_pic: MultipartBody.Part
    ):UploadResponse

    suspend fun uploadVideo(
        id: Int,
        video: MultipartBody.Part
    ): UploadResponse

    suspend fun uploadCredential(
        id: Int,
        credentials: MultipartBody.Part
    ): UploadResponse

    suspend fun getTutorClassesRequest(param:Params.TutorClassesRequest):List<TutorRequestDataModel>

    suspend fun getTutorClasses(param:Params.TutorClassesRequest):List<TutorRequestDataModel>

    suspend fun getTutorId(): UserEntity
}