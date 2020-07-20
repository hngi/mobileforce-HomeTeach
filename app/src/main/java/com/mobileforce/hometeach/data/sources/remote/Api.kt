package com.mobileforce.hometeach.data.sources.remote


import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Response
import retrofit2.http.*


interface  Api{

    @JvmSuppressWildcards
    @POST("v1/login/")

    suspend fun login(@Body params: Map<String, Any>): LoginResponse


    @JvmSuppressWildcards
    @POST("v1/rest-auth/")
    suspend fun resetPassword(@Body params: Map<String, Any>): Response<EmailResponse>

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
    @GET("v1/tutor-profiles/{id}/")
    suspend fun getTutorDetails(@Path("id") id: Int): TutorDetailsResponse
    
    @POST("v1/credit-cards/")
    suspend fun saveUserCardDetails(@Body params: Map<String, Any>)

    @Multipart
    @PUT("v1/tutor-profiles/{id}/")
    suspend fun uploadTutorMedia(
        @Part("id") id: RequestBody,
        @Part profile_pic: MultipartBody.Part,
        @Part credentials: MultipartBody.Part,
        @Part video: MultipartBody.Part
    ): Response<UploadResponse>


    @POST("v1/submit-request/")
    suspend fun requestTutorService(@Body params: Params.RequestTutorService): Response<TutorServiceRequestResponse>

    @GET("v1/card-by-id/{id}/")
    suspend fun getUserCardDetails(@Path("id") id: String): List<UserCardDetailResponse>

    @GET("")
    suspend fun getUserCardDetails(@Path("id") id: Int): List<UserCardDetailResponse>
   
  @GET("v1/users/")
    suspend fun getUser(): LiveData<UserEntity>

    @GET("v1/profiles/{id}/")
    suspend fun getUserProfile(@Path("id") id: Int): StudentProfileResponse
}