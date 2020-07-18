package com.mobileforce.hometeach.data.repository

<<<<<<< HEAD
import com.mobileforce.hometeach.data.sources.remote.wrappers.TutorDetailsResponse
=======
import com.mobileforce.hometeach.data.sources.remote.wrappers.UploadResponse
import com.mobileforce.hometeach.remotesource.wrappers.TutorDetailsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
>>>>>>> 7a986b49b8a6aade3d51352a8a9ff91aea790e84

interface TutorRepository {
    suspend fun getTutorDetails(): TutorDetailsResponse

    suspend fun uploadTutorMedia(id: RequestBody,
                                 profile_pic: MultipartBody.Part,
                                 credentials: MultipartBody.Part,
                                 video: MultipartBody.Part): Response<UploadResponse>

    suspend fun getId():String
}