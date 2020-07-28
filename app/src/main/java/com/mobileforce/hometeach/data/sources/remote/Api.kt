package com.mobileforce.hometeach.data.sources.remote


import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.sources.local.entities.UserEntity
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.models.TutorRequestDataModel
import com.mobileforce.hometeach.models.TutorUpcomingDataModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface Api {

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
    suspend fun getTutorDetails(@Path("id") id: Int): UserProfileResponse

    @JvmSuppressWildcards
    @GET("v1/tutor-profiles/{id}/")
    suspend fun getTutorDetailsForUser(@Path("id") id: Int): Response<TutorDetailsResponse>

    @JvmSuppressWildcards
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

    @JvmSuppressWildcards
    @POST("v1/user-card-details/")
    suspend fun getUserCardDetails(@Body params: Map<String, String>): List<UserCardDetailResponse>

    @GET("v1/users/")
    suspend fun getUser(): LiveData<UserEntity>

    @GET("v1/profiles/{id}/")
    suspend fun getUserProfile(@Path("id") id: Int): UserProfileResponse

    @JvmSuppressWildcards
    @PUT("v1/tutor-profiles/{id}/")
    suspend fun updateTutorProfile(@Path("id") id:Int, @Body params:Map<String, String>): LoginResponse

    @JvmSuppressWildcards
    @Multipart
    @PUT("v1/tutor-profiles/{id}/")
    suspend fun uploadProfilePic(
        @Path("id") id: Int,
        @Part profile_pic: MultipartBody.Part
    ): UploadResponse

    @JvmSuppressWildcards
    @PUT("v1/tutor-profiles/{id}/")
    suspend fun uploadVideo(@Path("id") id: Int, @Part video: MultipartBody.Part): UploadResponse

    @JvmSuppressWildcards
    @PUT("v1/tutor-profiles/{id}/")
    suspend fun uploadCredential(
        @Path("id") id: Int,
        @Part credentials: MultipartBody.Part
    ): UploadResponse

    @JvmSuppressWildcards
    @POST("v1/student-classes-requests/")
    suspend fun getStudentClassRequest(@Body param: Map<String, String>): UserClassRequestResponse

    @POST("v1/student-classes/")
    suspend fun getStudentClasses(@Body param: Map<String, String>): UserClassesResponse

    @POST("v1/tutor-classes-requests/")
    suspend fun getTutorClassesRequest(@Body params: Map<String, String>): TutorRequestDataModel

    @POST("v1/tutor-classes/")
    suspend fun getTutorClasses(@Body params: Map<String, String>): TutorUpcomingDataModel

    @POST("v1/request-action/")
    suspend fun grantStudentRequest(@Body params: Map<String, Any>): StudentRequestResponse

    @GET("v1/user-wallet/{id}/")
    suspend fun getUserWallet(@Path("id") id: String): UserWalletResponse

    @POST("v1/profile-count-update/")
    suspend fun updateTutorProfileVisit(@Body param: Params.TutorProfileVisitsCount)

    @JvmSuppressWildcards
    @PUT("v1/student-profiles/{id}/")
    suspend fun updateStudentProfile(@Path("id") id: Int, @Body params: Map<String, String>): UserProfileResponse

    @JvmSuppressWildcards
    @Multipart
    @PUT("v1/student-profiles/{id}/")
    suspend fun uploadStudentProfilePic(@Path("id") id: Int, @Part profilePic: MultipartBody.Part): UploadResponse

}