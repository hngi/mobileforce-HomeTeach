package com.mobileforce.hometeach.data

import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.repo.UserRepository
import com.mobileforce.hometeach.data.sources.DataSourceFactory
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse


class UserRepositoryImpl(private val dataSource: DataSourceFactory) : UserRepository {

    override suspend fun login(params: Params.SignIn): LoginResponse {
        return dataSource.remote().logIn()
    }

    override fun register() {
        TODO("Not yet implemented")
    }

    override fun saveUser(user: User) {
        dataSource.local().saveUser(user)
    }

    override fun logOut() {
        TODO("Not yet implemented")
    }


}