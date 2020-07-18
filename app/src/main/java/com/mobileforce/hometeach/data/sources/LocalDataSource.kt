package com.mobileforce.hometeach.data.sources

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.ProfileEntity
import com.mobileforce.hometeach.data.model.TutorEntity
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.sources.local.AppDataBase
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.TutorDetailsResponse
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import retrofit2.Response

class LocalDataSource(private val db: AppDataBase) : DataSource {

    override suspend fun logIn(params: Params.SignIn): LoginResponse {
        TODO("Not yet implemented")
    }


    override suspend fun signUp(params: Params.SignUp): RegisterUserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun saveUser(user: User) {
        db.userDao().saveUser(mapUserToEntity(user))
    }


    override fun getUser(): LiveData<UserEntity> {
        return db.userDao().getUser()
    }

    override suspend fun getSingleUser(): UserEntity {
        return db.userDao().getSingleUser()
    }

    override suspend fun clearDb() {
        db.userDao().clearDb()
    }

    override suspend fun resetPassword(params: Params.PasswordReset): EmailResponse {
        TODO("Not yet implemented")
    }


    override suspend fun editTutorProfile(
        id: Int,
        params: Params.EditTutorProfile
    ): EditTutorProfileResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getProfileList(): List<ProfileResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTutorDetails(
        id: Int
    ): TutorDetailsResponse {
        TODO("Not yet implemented")
    }


    override suspend fun getTutorList(): Response<List<TutorListResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun requestTutorService(params: Params.RequestTutorService): Response<TutorServiceRequestResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTutorList(tutorList: List<TutorEntity>) {
        db.tutorListDao().saveTutors(tutorList)
    }

    override fun searchTutors(query: String): LiveData<List<TutorEntity>> {
        return db.tutorListDao().getSearchTutor(query)
    }

    override suspend fun clearTutorListDb() {
        db.tutorListDao().clearDatabase()
    }

    override suspend fun getTutorListDb(): List<TutorEntity> {
        return db.tutorListDao().getTutors()
    }

    override suspend fun saveUserCardDetails(params: Params.CardDetails) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserCardDetails(id: Int): List<UserCardDetailResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveUserProfile(profile: Profile) {
        db.userDao().saveUserProfile(mapProfileToEntity(profile))
    }

    override fun profileLiveData(): LiveData<ProfileEntity> {
        return db.userDao().observeableProfileData()
    }


    private fun mapUserToEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            is_tutor = user.isTutor,
            email = user.email,
            phone_number = user.phoneNumber,
            token = user.token,
            full_name = user.fullName
        )
    }

    private fun mapProfileToEntity(profile: Profile): ProfileEntity {
        return ProfileEntity(
            profile.id,
            profile.profile_pic,
            profile.hourly_rate,
            profile.desc,
            profile.field,
            profile.major_course,
            profile.other_courses,
            profile.state,
            profile.address,
            profile.user_url,
            rating = profile.rating?.rating, rating_count = profile.rating?.count

        )
    }
}