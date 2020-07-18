package com.mobileforce.hometeach.data.sources

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.ProfileEntity
import com.mobileforce.hometeach.data.model.TutorEntity
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.sources.remote.Api
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.TutorDetailsResponse
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import retrofit2.Response

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

    override suspend fun getTutorList(): Response<List<TutorListResponse>> {
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

    override suspend fun saveUserProfile(profile: Profile) {
        TODO("Not yet implemented")
    }

    override fun profileLiveData(): LiveData<ProfileEntity> {
        TODO("Not yet implemented")
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

    override suspend fun resetPassword(params: Params.PasswordReset): EmailResponse {

        val map = hashMapOf(
            "email" to params.email
        )
        return api.resetPassword(map)
    }

}

