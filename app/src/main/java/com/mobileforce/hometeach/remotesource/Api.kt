package com.mobileforce.hometeach.remotesource

import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse
import com.mobileforce.hometeach.remotesource.wrappers.RegisterUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface Api {

    @POST("api/v1/login/")
    suspend fun login(@Body params: Params.SignIn): LoginResponse

    @POST("api/v1/register/")
    suspend fun register(@Body params: Params.SignUp): RegisterUserResponse
}