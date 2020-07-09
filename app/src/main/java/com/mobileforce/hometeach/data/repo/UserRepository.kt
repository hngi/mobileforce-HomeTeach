package com.mobileforce.hometeach.data.repo

import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse


interface UserRepository {

    suspend fun login(params: Params.SignIn): LoginResponse
    fun register()
    fun saveUser(user: User)
    fun logOut()
}