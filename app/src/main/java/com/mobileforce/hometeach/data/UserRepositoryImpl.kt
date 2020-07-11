package com.mobileforce.hometeach.data

import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.repo.UserRepository
import com.mobileforce.hometeach.data.sources.DataSourceFactory
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.EditTutorProfileResponse
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse
import com.mobileforce.hometeach.remotesource.wrappers.ProfileResponse
import com.mobileforce.hometeach.remotesource.wrappers.RegisterUserResponse


class UserRepositoryImpl(private val dataSource: DataSourceFactory) : UserRepository {

    override suspend fun login(params: Params.SignIn): LoginResponse {
        return dataSource.remote().logIn()
    }

    override suspend fun register(params: Params.SignUp): RegisterUserResponse {
        return dataSource.remote().signUp(params)
    }

    override fun saveUser(user: User) {
        dataSource.local().saveUser(user)
    }

    override fun logOut() {
        TODO("Not yet implemented")
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


}