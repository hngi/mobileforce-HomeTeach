package com.mobileforce.hometeach.data.sources

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.localsource.model.UserEntity
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.*
import retrofit2.Response


interface DataSource {

    suspend fun logIn(params: Params.SignIn): Response<List<Any>>
    suspend fun signUp(params: Params.SignUp): RegisterUserResponse

    suspend fun resetPassword(params: Params.PasswordReset):EmailResponse

    suspend fun saveUser(user: User)
    fun getUser(): LiveData<UserEntity>
    suspend fun getSingleUser(): UserEntity
    suspend fun editTutorProfile(id: Int, params: Params.EditTutorProfile): EditTutorProfileResponse
    suspend fun getProfileList(): List<ProfileResponse>
    suspend fun getTutorDetails(id: Int): TutorDetailsResponse
    suspend fun clearDb()

}