package com.mobileforce.hometeach.data.sources

import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.localsource.model.UserEntity
import com.mobileforce.hometeach.remotesource.Api
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse

class RemoteDataSource(private val api: Api) : DataSource {

    override suspend fun logIn(): LoginResponse {
        return api.login()
    }

    override fun signUp() {
        TODO("Not yet implemented")
    }

    override fun saveUser(user: User) {
        TODO("Not yet implemented")
    }


    override suspend fun getUser(): UserEntity {
        TODO("Not yet implemented")
    }
}