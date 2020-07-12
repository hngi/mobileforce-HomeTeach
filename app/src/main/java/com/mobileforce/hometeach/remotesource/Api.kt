package com.mobileforce.hometeach.remotesource

import com.mobileforce.hometeach.remotesource.wrappers.EmailResponse
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse
import com.mobileforce.hometeach.remotesource.wrappers.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface Api {

    @GET("login/user")
    suspend fun login(): LoginResponse

    @POST("api/v1/register/")
    suspend fun register(@Body params: Params.SignUp): RegisterUserResponse

    @POST("api/v1/password_reset/")
    suspend fun password_reset(@Body params: Params.PasswordReset):EmailResponse
}