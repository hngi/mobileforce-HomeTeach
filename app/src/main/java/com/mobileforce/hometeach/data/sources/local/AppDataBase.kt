package com.mobileforce.hometeach.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobileforce.hometeach.data.model.ProfileEntity
import com.mobileforce.hometeach.data.model.TutorEntity
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.sources.local.dao.TutorListDao
import com.mobileforce.hometeach.data.sources.local.dao.UserDao


@Database(
    entities = [UserEntity::class, TutorEntity::class, ProfileEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun tutorListDao(): TutorListDao
}
