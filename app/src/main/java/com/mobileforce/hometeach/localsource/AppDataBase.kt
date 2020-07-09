package com.mobileforce.hometeach.localsource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobileforce.hometeach.localsource.dao.UserDao
import com.mobileforce.hometeach.localsource.model.UserEntity


@Database(entities = [UserEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
