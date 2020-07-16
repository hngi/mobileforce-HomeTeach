package com.mobileforce.hometeach.data.sources.remote


import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Response
import retrofit2.http.*


interface Api {

    @JvmSuppressWildcards
    @POST("v1/login/")
    suspend fun login(@Body params: Map<String, Any>): Response<List<Any>>

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

    @GET("api/v1/tutor-profiles/")
    suspend fun getTutorList(): Response<TutorListResponse>

    @JvmSuppressWildcards
    @GET("v1/tutor_profiles/{id}/")
    suspend fun getTutorDetails(@Path("id") id: Int): TutorDetailsResponse

    @Multipart
    @PUT("v1/tutor-profiles/{id}/")
    suspend fun uploadTutorMedia(
        @Part("id") id: RequestBody,
        @Part profile_pic: MultipartBody.Part,
        @Part credentials: MultipartBody.Part,
        @Part video: MultipartBody.Part
    ): Response<UploadResponse>

}