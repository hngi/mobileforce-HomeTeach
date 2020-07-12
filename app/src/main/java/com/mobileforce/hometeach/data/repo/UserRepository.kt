package com.mobileforce.hometeach.data.repo

import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.EditTutorProfileResponse
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse
import com.mobileforce.hometeach.remotesource.wrappers.ProfileResponse
import com.mobileforce.hometeach.remotesource.wrappers.RegisterUserResponse


interface UserRepository {

    suspend fun login(params: Params.SignIn): LoginResponse
    suspend fun register(params: Params.SignUp): RegisterUserResponse
    fun saveUser(user: User)
    fun logOut()
    suspend fun editTutorProfile(id: Int, params: Params.EditTutorProfile): EditTutorProfileResponse
    suspend fun getProfileList(): List<ProfileResponse>
}