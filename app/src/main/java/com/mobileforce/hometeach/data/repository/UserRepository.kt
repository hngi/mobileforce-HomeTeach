package com.mobileforce.hometeach.data.repository

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.ProfileEntity
import com.mobileforce.hometeach.data.model.TutorEntity
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
<<<<<<< HEAD
=======
import com.mobileforce.hometeach.remotesource.wrappers.TutorDetailsResponse
>>>>>>> d4cb0fae9367a2886e0ddf61d2cb6374c803d499
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import retrofit2.Response


interface UserRepository {

    suspend fun login(params: Params.SignIn): LoginResponse

    suspend fun register(params: Params.SignUp): RegisterUserResponse
<<<<<<< HEAD

=======
>>>>>>> d4cb0fae9367a2886e0ddf61d2cb6374c803d499
    suspend fun passwordReset(params: Params.PasswordReset):Response<EmailResponse>

    suspend fun saveUser(user: User)

    suspend fun logOut()

    suspend fun editTutorProfile(id: Int, params: Params.EditTutorProfile): EditTutorProfileResponse

    suspend fun getProfileList(): List<ProfileResponse>

    suspend fun getTutorDetails(): TutorDetailsResponse

    fun getUser(): LiveData<UserEntity>

    suspend fun getTutorList(): Response<List<TutorNetworkResponse>>
<<<<<<< HEAD

    suspend fun requestTutorService(params: Params.RequestTutorService): Response<TutorServiceRequestResponse>

    suspend fun saveTutorList(tutorList: List<TutorEntity>)

    fun searchTutor(query: String): LiveData<List<TutorEntity>>

    suspend fun clearTutorListDb()

    suspend fun getTutorListDb(): List<TutorEntity>

    suspend fun saveUserCardDetails(params: Params.CardDetails)

    suspend fun getUserCardDetails(id: String): List<UserCardDetailResponse>

    suspend fun getSingleUser(): UserEntity

    suspend fun saveUserProfile(profile: Profile)
=======
    suspend fun requestTutorService(params: Params.RequestTutorService): Response<TutorServiceRequestResponse>
    suspend fun saveTutorList(tutorList: List<TutorEntity>)
    fun searchTutor(query: String): LiveData<List<TutorEntity>>
    suspend fun clearTutorListDb()
    suspend fun getTutorListDb(): List<TutorEntity>

    suspend fun saveUserCardDetails(params: Params.CardDetails)
    suspend fun getUserCardDetails(id: Int): List<UserCardDetailResponse>

    suspend fun saveUserProfile(user: Profile)
>>>>>>> d4cb0fae9367a2886e0ddf61d2cb6374c803d499
    fun profileLiveData(): LiveData<ProfileEntity>


}