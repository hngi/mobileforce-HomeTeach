package com.mobileforce.hometeach.localsource.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mobileforce.hometeach.localsource.model.UserEntity

@Dao
interface UserDao {

    @Insert
    fun saveUser(user: UserEntity)

    @Query("SELECT * FROM user")
    suspend fun getUser(): UserEntity

    @Delete
    fun deleteUser(user: UserEntity)


}