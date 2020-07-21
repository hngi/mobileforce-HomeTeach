package com.mobileforce.hometeach.data.sources

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.ProfileEntity
import com.mobileforce.hometeach.data.model.TutorEntity
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import retrofit2.Response
import retrofit2.http.Part
import java.io.InputStream


interface DataSource {


    suspend fun logIn(params: Params.SignIn): LoginResponse

    suspend fun signUp(params: Params.SignUp): RegisterUserResponse

    suspend fun resetPassword(params: Params.PasswordReset):Response<EmailResponse>

    suspend fun saveUser(user: User)

    fun getUser(): LiveData<UserEntity>

    suspend fun getSingleUser(): UserEntity

    suspend fun editTutorProfile(id: Int, params: Params.EditTutorProfile): EditTutorProfileResponse

    suspend fun getProfileList(): List<ProfileResponse>

    suspend fun getTutorDetails(id: Int): TutorDetailsResponse

    suspend fun clearDb()

    suspend fun getTutorList() : Response<List<TutorNetworkResponse>>

    suspend fun requestTutorService(params: Params.RequestTutorService): Response<TutorServiceRequestResponse>

    suspend fun saveTutorList(tutorList: List<TutorEntity>)

    fun searchTutors(query: String) : LiveData<List<TutorEntity>>

    suspend fun clearTutorListDb()

    suspend fun getTutorListDb(): List<TutorEntity>

    suspend fun saveUserCardDetails(params: Params.CardDetails)

    suspend fun getUserCardDetails(params: Params.UserID): List<UserCardDetailResponse>

    suspend fun uploadTutorMedia( id: RequestBody,
                                 profile_pic: MultipartBody.Part,
                                 credentials: MultipartBody.Part,
                                 video: MultipartBody.Part):Response<UploadResponse>

    suspend fun getId():String

    suspend fun saveUserProfile(profile: Profile)

    fun profileLiveData(): LiveData<ProfileEntity>

    suspend fun getUserProfile(id: Int): StudentProfileResponse

    suspend fun getSingleUserProfile(): ProfileEntity

}