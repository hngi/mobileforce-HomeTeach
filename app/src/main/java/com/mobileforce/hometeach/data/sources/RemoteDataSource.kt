package com.mobileforce.hometeach.data.sources

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.UploadResponse
import com.mobileforce.hometeach.data.model.TutorEntity
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.sources.remote.Api
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Part

class RemoteDataSource(private val api: Api) : DataSource {

    override suspend fun logIn(params: Params.SignIn): LoginResponse {
        val map = hashMapOf(
            "email" to params.email,
            "password" to params.password
        )
        return api.login(map)
    }

    override suspend fun signUp(params: Params.SignUp): RegisterUserResponse {

        val map = hashMapOf(
            "full_name" to params.full_name,
            "email" to params.email,
            "organization_email" to params.organization_email,
            "phone_number" to params.phone_number,
            "is_tutor" to params.is_tutor,
            "password" to params.password
        )
        return api.register(map)
    }

    override suspend fun saveUser(user: User) {
        TODO("Not yet implemented")
    }


    override fun getUser(): LiveData<UserEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getSingleUser(): UserEntity {
        TODO("Not yet implemented")
    }

    override suspend fun editTutorProfile(
        id: Int,
        params: Params.EditTutorProfile
    ): EditTutorProfileResponse {
        return api.editTutorProfile(id, params)
    }

    override suspend fun getProfileList(): List<ProfileResponse> {
        return api.getProfileList()
    }

    override suspend fun getTutorDetails(
        id: Int
    ): TutorDetailsResponse {
        return api.getTutorDetails(id)
    }

    override suspend fun clearDb() {
        TODO("Not yet implemented")
    }

    override suspend fun getTutorList(): Response<List<TutorNetworkResponse>> {
        return api.getTutorList()
    }

    override suspend fun saveUserCardDetails(params: Params.CardDetails) {
        val map = hashMapOf(
            "card_number" to params.card_number,
            "card_cvv" to params.card_cvc,
            "expiry_month" to params.expiry_month,
            "expiry_year" to params.expiry_year
        )
        api.saveUserCardDetails(map)
    }

    override suspend fun getUserCardDetails(id: Int): List<UserCardDetailResponse> {
        return api.getUserCardDetails(id)
    }

    override suspend fun uploadTutorMedia(
        id: RequestBody,
        profile_pic: MultipartBody.Part,
        credentials: MultipartBody.Part,
        video: MultipartBody.Part
    ): Response<UploadResponse> {
        return api.uploadTutorMedia(id, profile_pic, credentials, video)
    }

    override suspend fun getId(): String {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(params: Params.PasswordReset): Response<EmailResponse> {
        val map = hashMapOf(
            "email" to params.email
        )
        return api.resetPassword(map)
    }

    override suspend fun loadDocument(@Part document: MultipartBody.Part, @Part("desc") desc: RequestBody): Call<UploadResponse> {
        TODO()
    }

        override suspend fun requestTutorService(params: Params.RequestTutorService): Response<TutorServiceRequestResponse> {
            return api.requestTutorService(params)
        }

        override suspend fun saveTutorList(tutorList: List<TutorEntity>) {
            TODO("Not yet implemented")
        }

        override fun searchTutors(query: String): LiveData<List<TutorEntity>> {
            TODO("Not yet implemented")
        }

        override suspend fun clearTutorListDb() {
            TODO("Not yet implemented")
        }

        override suspend fun getTutorListDb(): List<TutorEntity> {
            TODO("Not yet implemented")
        }


}

