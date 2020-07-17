package com.mobileforce.hometeach.data.sources

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.UploadResponse
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response


interface DataSource {

    suspend fun logIn(params: Params.SignIn): LoginResponse
    suspend fun signUp(params: Params.SignUp): RegisterUserResponse
    suspend fun resetPassword(params: Params.PasswordReset):EmailResponse
    suspend fun saveUser(user: User)
    fun getUser(): LiveData<UserEntity>
    suspend fun getSingleUser(): UserEntity
    suspend fun editTutorProfile(id: Int, params: Params.EditTutorProfile): EditTutorProfileResponse
    suspend fun getProfileList(): List<ProfileResponse>
    suspend fun getTutorDetails(id: Int): TutorDetailsResponse
    suspend fun clearDb()
    suspend fun getTutorList() : Response<TutorListResponse>
    suspend fun saveUserCardDetails(params: Params.CardDetails)
    suspend fun getUserCardDetails(id: Int): List<UserCardDetailResponse>
    suspend fun loadDocument(document: MultipartBody.Part, desc: RequestBody): Call<UploadResponse>

}