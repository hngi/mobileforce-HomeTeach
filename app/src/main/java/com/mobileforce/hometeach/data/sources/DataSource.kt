package com.mobileforce.hometeach.data.sources

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.localsource.model.UserEntity
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.EmailResponse
import com.mobileforce.hometeach.remotesource.wrappers.EditTutorProfileResponse
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse
import com.mobileforce.hometeach.remotesource.wrappers.ProfileResponse
import com.mobileforce.hometeach.remotesource.wrappers.RegisterUserResponse
import retrofit2.Response


interface DataSource {

    suspend fun logIn(params: Params.SignIn): Response<List<Any>>
    suspend fun signUp(params: Params.SignUp): RegisterUserResponse

    suspend fun password_reset(params: Params.PasswordReset):EmailResponse

    suspend fun saveUser(user: User)
    fun getUser(): LiveData<UserEntity>
    suspend fun editTutorProfile(id: Int, params: Params.EditTutorProfile): EditTutorProfileResponse
    suspend fun getProfileList(): List<ProfileResponse>
    suspend fun clearDb()

}