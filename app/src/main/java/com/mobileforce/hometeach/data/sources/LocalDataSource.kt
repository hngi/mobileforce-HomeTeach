package com.mobileforce.hometeach.data.sources

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.sources.local.AppDataBase
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.*

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
    override suspend fun clearDb() {
        db.userDao().clearDb()
    }


    override suspend fun getTutorList(): Response<TutorListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveUserCardDetails(params: Params.CardDetails) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserCardDetails(id: Int): List<UserCardDetailResponse> {
        TODO("Not yet implemented")
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
}