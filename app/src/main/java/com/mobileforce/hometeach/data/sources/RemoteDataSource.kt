package com.mobileforce.hometeach.data.sources

import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.localsource.model.UserEntity
import com.mobileforce.hometeach.remotesource.Api
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.EmailResponse
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse
import com.mobileforce.hometeach.remotesource.wrappers.RegisterUserResponse

class RemoteDataSource(private val api: Api) : DataSource {

    override suspend fun logIn(): LoginResponse {
        return api.login()
    }

    override suspend fun signUp(params: Params.SignUp): RegisterUserResponse {
        return api.register(params)
    }

    override fun saveUser(user: User) {
        TODO("Not yet implemented")
    }


    override suspend fun getUser(): UserEntity {
        TODO("Not yet implemented")
    }

    override suspend fun password_reset(params: Params.PasswordReset): EmailResponse {
        return api.password_reset(params)
    }

}

