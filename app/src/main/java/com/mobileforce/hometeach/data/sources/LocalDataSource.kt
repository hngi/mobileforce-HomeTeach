package com.mobileforce.hometeach.data.sources

import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.localsource.AppDataBase
import com.mobileforce.hometeach.localsource.model.UserEntity
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse

class LocalDataSource(private val db: AppDataBase) : DataSource {

    override suspend fun logIn(): LoginResponse {
        TODO("Not yet implemented")
    }


    override fun signUp() {
        TODO("Not yet implemented")
    }

    override fun saveUser(user: User) {

        db.userDao().saveUser(mapUserToEntity(user))
    }


    override suspend fun getUser(): UserEntity {
        return db.userDao().getUser()
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