package com.mobileforce.hometeach.remotesource

import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse
import com.mobileforce.hometeach.remotesource.wrappers.RegisterUserResponse
import retrofit2.http.GET
import retrofit2.http.POST


interface Api {

    @GET("login/user")
    suspend fun login(): LoginResponse

    @POST("api/v1/register")
    suspend fun register(params: Params.SignUp): RegisterUserResponse
}