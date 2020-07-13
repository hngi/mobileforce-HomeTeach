package com.mobileforce.hometeach.data.sources

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.localsource.AppDataBase
import com.mobileforce.hometeach.localsource.model.UserEntity
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.EditTutorProfileResponse
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse
import com.mobileforce.hometeach.remotesource.wrappers.ProfileResponse
import com.mobileforce.hometeach.remotesource.wrappers.RegisterUserResponse
import retrofit2.Response

class LocalDataSource(private val db: AppDataBase) : DataSource {

    override suspend fun logIn(params: Params.SignIn): Response<List<Any>> {
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

    override suspend fun editTutorProfile(
        id: Int,
        params: Params.EditTutorProfile
    ): EditTutorProfileResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getProfileList(): List<ProfileResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun clearDb() {
        db.userDao().clearDb()
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