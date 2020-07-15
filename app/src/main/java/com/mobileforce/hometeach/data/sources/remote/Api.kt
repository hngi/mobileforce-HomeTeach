package com.mobileforce.hometeach.data.sources.remote

import com.mobileforce.hometeach.data.sources.remote.wrappers.*

import retrofit2.Response
import retrofit2.http.*


interface Api {

    @JvmSuppressWildcards
    @POST("api/v1/login/")
    suspend fun login(@Body params: Map<String, Any>): Response<List<Any>>

    @JvmSuppressWildcards
    @POST("api/v1/password-reset/")
    suspend fun resetPassword(@Body params: Map<String, String>): EmailResponse

    @JvmSuppressWildcards
    @POST("api/v1/register/")
    suspend fun register(@Body params: Map<String, Any>): RegisterUserResponse

    @PUT("api/v1/profiles/{id}/")
    suspend fun editTutorProfile(
        @Path("id") id: Int,
        @Body params: Params.EditTutorProfile
    ): EditTutorProfileResponse

    @GET("/api/v1/profiles/")
    suspend fun getProfileList(): List<ProfileResponse>

    @GET("api/v1/tutor-profiles/")
    suspend fun getTutorList(): Response<TutorListResponse>
}