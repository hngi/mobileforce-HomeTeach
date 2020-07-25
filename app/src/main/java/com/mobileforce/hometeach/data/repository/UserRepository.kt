package com.mobileforce.hometeach.data.repository

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.sources.local.entities.*
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserCardDetailResponse
import retrofit2.Response


interface UserRepository {

    suspend fun login(params: Params.SignIn): LoginResponse

    suspend fun register(params: Params.SignUp): RegisterUserResponse

    suspend fun passwordReset(params: Params.PasswordReset): Response<EmailResponse>

    suspend fun saveUser(user: User)

    suspend fun logOut()

    suspend fun editTutorProfile(id: Int, params: Params.EditTutorProfile): EditTutorProfileResponse

    suspend fun getProfileList(): List<ProfileResponse>

    suspend fun getTutorDetails(): UserProfileResponse

    fun getUser(): LiveData<UserEntity>

    suspend fun getTutorList(): Response<List<TutorNetworkResponse>>

    suspend fun requestTutorService(params: Params.RequestTutorService): Response<TutorServiceRequestResponse>

    suspend fun saveTutorList(tutorList: List<TutorEntity>)

    fun searchTutor(query: String): LiveData<List<TutorEntity>>

    suspend fun clearTutorListDb()

    suspend fun getTutorListDb(): List<TutorEntity>

    suspend fun saveUserCardDetails(params: Params.CardDetails)


    suspend fun getUserCardDetails(): List<UserCardDetailResponse>

    suspend fun getSingleUser(): UserEntity

    suspend fun saveUserProfile(profile: Profile)
    fun profileLiveData(): LiveData<ProfileEntity>

    suspend fun modify()

    suspend fun save()

    suspend fun getSingleUserProfile(): ProfileEntity

    suspend fun getUserProfile(): UserProfileResponse

    suspend fun getTutorDetailsForUser(id: Int): Response<TutorDetailsResponse>

    suspend fun getTutorDetailsForUserDb(id: Int): TutorDetailsEntity?

    suspend fun saveTutorDetailsForUserDb(tutorDetailsEntity: TutorDetailsEntity)

    suspend fun getStudentClass(): UserClassResponse

    suspend fun getUserWallet(): UserWalletResponse

    suspend fun saveWallet(walletData: WalletData)

    fun observeWalletData(): LiveData<WalletEntity>

}

