package com.mobileforce.hometeach.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobileforce.hometeach.data.sources.local.dao.UserDao
import com.mobileforce.hometeach.data.model.UserEntity


@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
