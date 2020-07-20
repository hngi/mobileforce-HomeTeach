package com.mobileforce.hometeach.data.repository

import com.mobileforce.hometeach.data.model.ProfileEntity
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.LoginResponse
import com.mobileforce.hometeach.data.sources.remote.wrappers.TutorDetailsResponse
import com.mobileforce.hometeach.data.sources.remote.wrappers.UpdateTutorResponse
import com.mobileforce.hometeach.data.sources.remote.wrappers.UploadResponse
import com.mobileforce.hometeach.utils.UploadaResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface TutorRepository {
    suspend fun getTutorDetails(): TutorDetailsResponse

    suspend fun uploadTutorMedia(id: RequestBody,
                                 profile_pic: MultipartBody.Part,
                                 credentials: MultipartBody.Part,
                                 video: MultipartBody.Part): Response<UploadResponse>


    suspend fun updateTutorProfile(id:Int,params:Params.UpdateTutorProfile):Response<LoginResponse>

    suspend fun getProfileId(): ProfileEntity
    suspend fun uploadProfilePic(
        id: Int,
        profile_pic: MultipartBody.Part
    ): Response<UploadResponse>

    suspend fun uploadVideo(
        id: Int,
        video: MultipartBody.Part
    ): Response<UploadResponse>

    suspend fun uploadCredential(
        id: Int,
        credentials: MultipartBody.Part
    ): Response<UploadResponse>
}