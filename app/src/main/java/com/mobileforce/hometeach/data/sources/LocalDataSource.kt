package com.mobileforce.hometeach.data.sources

import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.localsource.AppDataBase
import com.mobileforce.hometeach.localsource.model.UserEntity
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.EmailResponse
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse
import com.mobileforce.hometeach.remotesource.wrappers.RegisterUserResponse

class LocalDataSource(private val db: AppDataBase) : DataSource {

    override suspend fun logIn(): LoginResponse {
        TODO("Not yet implemented")
    }


    override suspend fun signUp(params: Params.SignUp): RegisterUserResponse {
        TODO("Not yet implemented")
    }

    override fun saveUser(user: User) {

        db.userDao().saveUser(mapUserToEntity(user))
    }


    override suspend fun getUser(): UserEntity {
        return db.userDao().getUser()
    }

    override suspend fun password_reset(params: Params.PasswordReset): EmailResponse {
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