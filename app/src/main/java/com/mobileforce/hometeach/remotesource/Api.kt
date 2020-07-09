package com.mobileforce.hometeach.remotesource

import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse
import retrofit2.http.GET


interface Api {

    @GET("login/user")
    suspend fun login(): LoginResponse
}