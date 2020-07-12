package com.mobileforce.hometeach.data.repo

import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.EmailResponse
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse
import com.mobileforce.hometeach.remotesource.wrappers.RegisterUserResponse


interface UserRepository {

    suspend fun login(params: Params.SignIn): LoginResponse
    suspend fun register(params: Params.SignUp): RegisterUserResponse
    fun saveUser(user: User)
    fun logOut()
    suspend fun password_reset(params: Params.PasswordReset):EmailResponse

}