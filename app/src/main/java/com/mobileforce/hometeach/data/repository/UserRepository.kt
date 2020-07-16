package com.mobileforce.hometeach.data.repository

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.*

import retrofit2.Response


interface UserRepository {

    suspend fun login(params: Params.SignIn): LoginResponse
    suspend fun register(params: Params.SignUp): RegisterUserResponse
    suspend fun passwordReset(params: Params.PasswordReset):EmailResponse

    suspend fun saveUser(user: User)
    suspend fun logOut()
    suspend fun editTutorProfile(id: Int, params: Params.EditTutorProfile): EditTutorProfileResponse
    suspend fun getProfileList(): List<ProfileResponse>
    suspend fun getTutorDetails(): TutorDetailsResponse
    fun getUser(): LiveData<UserEntity>
    suspend fun getTutorList(): Response<TutorListResponse>
    suspend fun saveUserCardDetails(params: Params.CardDetails)
    suspend fun getUserCardDetails(id: Int): List<UserCardDetailResponse>
}