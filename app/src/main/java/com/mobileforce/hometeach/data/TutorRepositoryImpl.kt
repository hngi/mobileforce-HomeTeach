package com.mobileforce.hometeach.data

import com.mobileforce.hometeach.data.model.ProfileEntity
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.repository.TutorRepository
import com.mobileforce.hometeach.data.sources.DataSourceFactory
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.LoginResponse
import com.mobileforce.hometeach.data.sources.remote.wrappers.UploadResponse
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserProfileResponse
import com.mobileforce.hometeach.models.TutorRequestDataModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class TutorRepositoryImpl (private val dataSource: DataSourceFactory): TutorRepository {
    override suspend fun getTutorDetails(): UserProfileResponse {
        val user = dataSource.local().getSingleUserProfile()
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



    override suspend fun updateTutorProfile(
        id: Int,
        params: Params.UpdateTutorProfile
    ):LoginResponse{
        return dataSource.remote().updateTutorProfile(id,params)
    }

    override suspend fun getProfileId(): ProfileEntity {
        return dataSource.local().getSingleUserProfile()
    }

    override suspend fun uploadProfilePic(
        id: Int,
        profile_pic: MultipartBody.Part
    ): UploadResponse{
        return dataSource.remote().uploadProfilePic(id,profile_pic)
    }

    override suspend fun uploadVideo(id: Int, video: MultipartBody.Part):UploadResponse {
       return dataSource.remote().uploadVideo(id,video)
    }

    override suspend fun uploadCredential(
        id: Int,
        credentials: MultipartBody.Part
    ):UploadResponse{
        return dataSource.remote().uploadCredential(id,credentials)
    }

    override suspend fun getTutorClassesRequest(param: Params.TutorClassesRequest): List<TutorRequestDataModel> {
        return dataSource.remote().getTutorClassesRequest(param)
    }

    override suspend fun getTutorClasses(param: Params.TutorClassesRequest): List<TutorRequestDataModel> {
        return dataSource.remote().getTutorClasses(param)
    }

    override suspend fun getTutorId(): UserEntity {
        return dataSource.local().getSingleUser()
    }


}