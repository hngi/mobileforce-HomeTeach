package com.mobileforce.hometeach.data.repo

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.localsource.model.UserEntity
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.*

import retrofit2.Response


interface UserRepository {

    suspend fun login(params: Params.SignIn): Response<List<Any>>
    suspend fun register(params: Params.SignUp): RegisterUserResponse
    suspend fun password_reset(params: Params.PasswordReset):EmailResponse

    suspend fun saveUser(user: User)
    suspend fun logOut()
    suspend fun editTutorProfile(id: Int, params: Params.EditTutorProfile): EditTutorProfileResponse
    suspend fun getProfileList(): List<ProfileResponse>
    suspend fun getTutorDetails(): TutorDetailsResponse
    fun getUser(): LiveData<UserEntity>
}