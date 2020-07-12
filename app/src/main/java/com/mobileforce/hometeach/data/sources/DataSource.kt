package com.mobileforce.hometeach.data.sources

import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.localsource.model.UserEntity
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.EmailResponse
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse
import com.mobileforce.hometeach.remotesource.wrappers.RegisterUserResponse


interface DataSource {

    suspend fun logIn(): LoginResponse
    suspend fun signUp(params: Params.SignUp): RegisterUserResponse
    fun saveUser(user: User)
    suspend fun getUser(): UserEntity
    suspend fun password_reset(params: Params.PasswordReset):EmailResponse
}