package com.mobileforce.hometeach.data

import com.mobileforce.hometeach.data.repo.TutorRepository
import com.mobileforce.hometeach.data.sources.DataSourceFactory
import com.mobileforce.hometeach.data.sources.remote.wrappers.UploadResponse
import com.mobileforce.hometeach.remotesource.wrappers.TutorDetailsResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class TutorRepositoryImpl (private val dataSource: DataSourceFactory): TutorRepository {
    override suspend fun getTutorDetails(): TutorDetailsResponse {
        val user = dataSource.local().getSingleUser()
        return dataSource.remote().getTutorDetails(user.id.toInt())
    }

    override suspend fun uploadTutorMedia(
        id: RequestBody,
        profile_pic: MultipartBody.Part,
        credentials: MultipartBody.Part,
        video: MultipartBody.Part
    ): Response<UploadResponse> {
       return dataSource.remote().uploadTutorMedia(id,profile_pic, credentials, video)
    }

    override suspend fun getId(): String {
       return dataSource.local().getSingleUser().id
    }


}