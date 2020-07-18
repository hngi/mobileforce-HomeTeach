package com.mobileforce.hometeach.data

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.ProfileEntity
import com.mobileforce.hometeach.data.model.TutorEntity
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.DataSourceFactory
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.TutorDetailsResponse
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import retrofit2.Response


class UserRepositoryImpl(private val dataSource: DataSourceFactory) : UserRepository {

    override suspend fun login(params: Params.SignIn): LoginResponse {
        return dataSource.remote().logIn(params)
    }

    override suspend fun register(params: Params.SignUp): RegisterUserResponse {
        return dataSource.remote().signUp(params)
    }

    override suspend fun saveUser(user: User) {
        dataSource.local().saveUser(user)
    }

    override suspend fun logOut() {
        dataSource.local().clearDb()
    }

    override suspend fun editTutorProfile(
        id: Int,
        params: Params.EditTutorProfile
    ): EditTutorProfileResponse {
        return dataSource.remote().editTutorProfile(id, params)
    }

    override suspend fun getProfileList(): List<ProfileResponse> {
        return dataSource.remote().getProfileList()
    }

    override suspend fun getTutorDetails(): TutorDetailsResponse {
        val user = dataSource.local().getSingleUser()
        return dataSource.remote().getTutorDetails(user.id.toDouble().toInt())
    }

    override fun getUser(): LiveData<UserEntity> {
        return dataSource.local().getUser()
    }

    override suspend fun getTutorList(): Response<List<TutorListResponse>> {
        return dataSource.remote().getTutorList()
    }

    override suspend fun requestTutorService(params: Params.RequestTutorService): Response<TutorServiceRequestResponse> {
        return dataSource.remote().requestTutorService(params)
    }

    override suspend fun saveTutorList(tutorList: List<TutorEntity>) {
        dataSource.local().saveTutorList(tutorList)
    }

    override fun searchTutor(query: String): LiveData<List<TutorEntity>> {
        return dataSource.local().searchTutors(query)
    }

    override suspend fun clearTutorListDb() {
        dataSource.local().clearTutorListDb()
    }

    override suspend fun getTutorListDb(): List<TutorEntity> {
        return dataSource.local().getTutorListDb()
    }

    override suspend fun saveUserCardDetails(params: Params.CardDetails) {
        dataSource.remote().saveUserCardDetails(params)

    }

    override suspend fun getUserCardDetails(id: Int): List<UserCardDetailResponse> {
        return dataSource.remote().getUserCardDetails(id)
    }

    override suspend fun saveUserProfile(profile: Profile) {
        dataSource.local().saveUserProfile(profile)
    }

    override fun profileLiveData(): LiveData<ProfileEntity> {
        return dataSource.local().profileLiveData()
    }

    override suspend fun passwordReset(params: Params.PasswordReset): EmailResponse {
        return dataSource.remote().resetPassword(params)
    }
}