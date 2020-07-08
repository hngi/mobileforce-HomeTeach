package com.mobileforce.hometeach.data


interface UserRepository {

    fun login()
    fun register()
    fun saveUser()
    fun logOut()
}