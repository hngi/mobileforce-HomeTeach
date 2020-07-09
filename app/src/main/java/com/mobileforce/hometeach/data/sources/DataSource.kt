package com.mobileforce.hometeach.data.sources

import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.localsource.model.UserEntity
import com.mobileforce.hometeach.remotesource.wrappers.LoginResponse


interface DataSource {

    suspend fun logIn(): LoginResponse
    fun signUp()
    fun saveUser(user: User)
    suspend fun getUser(): UserEntity
}