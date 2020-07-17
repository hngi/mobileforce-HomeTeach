package com.mobileforce.hometeach.data.sources.remote


import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.*

import retrofit2.Response
import retrofit2.http.*


interface  Api{

    @JvmSuppressWildcards
    @POST("v1/login/")
    suspend fun login(@Body params: Map<String, Any>): LoginResponse

    @JvmSuppressWildcards
    @POST("v1/password-reset/")
    suspend fun resetPassword(@Body params: Map<String, String>): EmailResponse

    @JvmSuppressWildcards
    @POST("v1/register/")
    suspend fun register(@Body params: Map<String, Any>): RegisterUserResponse

    @PUT("v1/profiles/{id}/")
    suspend fun editTutorProfile(
        @Path("id") id: Int,
        @Body params: Params.EditTutorProfile
    ): EditTutorProfileResponse

    @GET("v1/profiles/")
    suspend fun getProfileList(): List<ProfileResponse>

    @GET("v1/tutor-profiles/")
    suspend fun getTutorList(): Response<List<TutorNetworkResponse>>

    @JvmSuppressWildcards
    @GET("v1/tutor_profiles/{id}/")
    suspend fun getTutorDetails(@Path("id") id: Int): TutorDetailsResponse
    
    @POST("")
    suspend fun saveUserCardDetails(@Body params: Map<String, Any>)

    @POST("v1/submit-request/")
    suspend fun requestTutorService(@Body params: Params.RequestTutorService): Response<TutorServiceRequestResponse>

    @GET("")
    suspend fun getUserCardDetails(@Path("id") id: Int): List<UserCardDetailResponse>
}