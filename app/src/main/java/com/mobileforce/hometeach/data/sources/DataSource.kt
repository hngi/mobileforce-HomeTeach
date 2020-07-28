package com.mobileforce.hometeach.data.sources

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.sources.local.entities.*
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.models.TutorRequestDataModel
import com.mobileforce.hometeach.models.TutorUpcomingDataModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response


interface DataSource {


    suspend fun logIn(params: Params.SignIn): LoginResponse

    suspend fun signUp(params: Params.SignUp): RegisterUserResponse

    suspend fun resetPassword(params: Params.PasswordReset): Response<EmailResponse>

    suspend fun saveUser(user: User)

    fun getUser(): LiveData<UserEntity>

    suspend fun getSingleUser(): UserEntity

    suspend fun editTutorProfile(id: Int, params: Params.EditTutorProfile): EditTutorProfileResponse

    suspend fun getProfileList(): List<ProfileResponse>

    suspend fun getTutorDetails(id: Int): UserProfileResponse

    suspend fun clearDb()

    suspend fun getTutorList(): Response<List<TutorNetworkResponse>>

    suspend fun requestTutorService(params: Params.RequestTutorService): Response<TutorServiceRequestResponse>

    suspend fun saveTutorList(tutorList: List<TutorEntity>)

    fun searchTutors(query: String): LiveData<List<TutorEntity>>

    suspend fun clearTutorListDb()

    suspend fun getTutorListDb(): List<TutorEntity>

    suspend fun saveUserCardDetails(params: Params.CardDetails)

    suspend fun getUserCardDetails(params: Params.UserID): List<UserCardDetailResponse>

    suspend fun getTutorDetailsForUser(id: Int): Response<TutorDetailsResponse>

    suspend fun getTutorDetailsFromDb(id: Int): TutorDetailsEntity?

    suspend fun saveTutorDetailsToDb(tutorDetailsEntity: TutorDetailsEntity)

    suspend fun uploadTutorMedia(
        id: RequestBody,
        profile_pic: MultipartBody.Part,
        credentials: MultipartBody.Part,
        video: MultipartBody.Part
    ): Response<UploadResponse>

    suspend fun uploadProfilePic(
        id: Int,
        profile_pic: MultipartBody.Part
    ): UploadResponse

    suspend fun uploadVideo(
        id: Int,
        video: MultipartBody.Part
    ): UploadResponse

    suspend fun uploadCredential(
        id: Int,
        credentials: MultipartBody.Part
    ): UploadResponse


    suspend fun saveUserProfile(profile: Profile)

    fun profileLiveData(): LiveData<ProfileEntity>

    suspend fun getUserProfile(id: Int): UserProfileResponse

    suspend fun getSingleUserProfile(): ProfileEntity

    suspend fun getProfileId(): Int

    suspend fun updateTutorProfile(
        id: Int,
        params: Params.UpdateTutorProfile
    ): LoginResponse


    suspend fun getTutorClassesRequest(param: Params.TutorClassesRequest): TutorRequestDataModel

    suspend fun getTutorClasses(param: Params.TutorClassesRequest): TutorUpcomingDataModel

    suspend fun grantStudentRequest(params: Params.StudentRequest): StudentRequestResponse

    suspend fun getStudentClassRequest(param: Params.StudentID): UserClassRequestResponse

    suspend fun getStudentClasses(param: Params.StudentID): UserClassesResponse

    suspend fun getUserWallet(param: Params.UserWallet): UserWalletResponse = TODO()

    suspend fun saveUserWallet(walletEntity: WalletEntity) {}

    fun observeWalletData(): LiveData<WalletEntity> = TODO()

    suspend fun saveCardToDb(cardEntity: CardEntity) {}

    fun observeUserCards(): LiveData<List<CardEntity>> = TODO()
}
