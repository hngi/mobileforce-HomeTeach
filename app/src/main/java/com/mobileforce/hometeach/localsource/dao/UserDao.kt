package com.mobileforce.hometeach.localsource.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mobileforce.hometeach.localsource.model.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)

    @Query("SELECT * FROM user")
    fun getUser(): LiveData<UserEntity>

    @Delete
    fun deleteUser(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun clearDb()


}