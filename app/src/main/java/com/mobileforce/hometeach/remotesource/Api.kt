package com.mobileforce.hometeach.remotesource

import com.mobileforce.hometeach.remotesource.wrappers.*

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
    @JvmSuppressWildcards
    @GET("api/v1/tutor_profiles/{id}/")
    suspend fun getTutorDetails(@Path("id") id: Int): TutorDetailsResponse

    @POST("")
    suspend fun saveUserCardDetails(@Body params: Map<String, Any>)

    @GET("")
    suspend fun getUserCardDetails(@Path("id") id: Int): List<UserCardDetailResponse>
}