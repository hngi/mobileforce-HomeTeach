package com.mobileforce.hometeach.data.repo

import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.EditTutorProfileResponse
import com.mobileforce.hometeach.remotesource.wrappers.ProfileResponse
import com.mobileforce.hometeach.remotesource.wrappers.RegisterUserResponse
import retrofit2.Response


interface UserRepository {

    suspend fun login(params: Params.SignIn): Response<List<Any>>
    suspend fun register(params: Params.SignUp): RegisterUserResponse
    suspend fun saveUser(user: User)
    fun logOut()
    suspend fun editTutorProfile(id: Int, params: Params.EditTutorProfile): EditTutorProfileResponse
    suspend fun getProfileList(): List<ProfileResponse>
}