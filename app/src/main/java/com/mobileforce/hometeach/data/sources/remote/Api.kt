package com.mobileforce.hometeach.data.sources.remote


import com.mobileforce.hometeach.data.model.UploadResponse
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
<<<<<<< HEAD
=======
import retrofit2.Call
<<<<<<< HEAD
>>>>>>> d4cb0fae9367a2886e0ddf61d2cb6374c803d499

=======
>>>>>>> 148cde10885453de2edcdd8d8ff27db782351896
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface  Api {

    @JvmSuppressWildcards
    @POST("v1/login/")

    suspend fun login(@Body params: Map<String, Any>): LoginResponse


    @JvmSuppressWildcards
    @POST("v1/rest-auth/")
    suspend fun resetPassword(@Body params: Map<String, Any>): Response<EmailResponse>
<<<<<<< HEAD
=======


    @Multipart
    @POST("v1/upload")
    suspend fun loadDocument(
        @Part document: MultipartBody.Part,
        @Body desc: RequestBody
    ): Call<UploadResponse>
>>>>>>> d4cb0fae9367a2886e0ddf61d2cb6374c803d499

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
<<<<<<< HEAD
    
    @POST("v1/credit-cards/")
=======

    @POST("")
>>>>>>> d4cb0fae9367a2886e0ddf61d2cb6374c803d499
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

<<<<<<< HEAD
    @GET("v1/card-by-id/{id}/")
    suspend fun getUserCardDetails(@Path("id") id: String): List<UserCardDetailResponse>

    @GET("")
    suspend fun getUserCardDetails(@Path("id") id: Int): List<UserCardDetailResponse>

=======
    @GET("")
    suspend fun getUserCardDetails(@Path("id") id: Int): List<UserCardDetailResponse>

<<<<<<< HEAD
=======
    @Multipart
    @POST("")
    fun loadDocument(
        @Part document: MultipartBody.Part,
        @Part("desc") desc: RequestBody
    ):Call<UploadResponse>

>>>>>>> 148cde10885453de2edcdd8d8ff27db782351896
    companion object {
        operator fun invoke(): Api {
            return Retrofit.Builder()
                .baseUrl("http://127.0.0.1/DocumentUploader/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
<<<<<<< HEAD

        fun loadDocument(@Part document: MultipartBody.Part, @Part("desc") desc: RequestBody):Call<UploadResponse>{
            return loadDocument(document,desc)
        }
    }
=======
    }

>>>>>>> 148cde10885453de2edcdd8d8ff27db782351896
>>>>>>> d4cb0fae9367a2886e0ddf61d2cb6374c803d499
}