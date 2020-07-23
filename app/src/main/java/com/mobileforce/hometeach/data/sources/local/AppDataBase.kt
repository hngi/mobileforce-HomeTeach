package com.mobileforce.hometeach.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobileforce.hometeach.data.sources.local.dao.TutorDetailsDao
import com.mobileforce.hometeach.data.sources.local.entities.ProfileEntity
import com.mobileforce.hometeach.data.sources.local.entities.TutorEntity
import com.mobileforce.hometeach.data.sources.local.entities.UserEntity
import com.mobileforce.hometeach.data.sources.local.dao.TutorListDao
import com.mobileforce.hometeach.data.sources.local.dao.UserDao
import com.mobileforce.hometeach.data.sources.local.entities.TutorDetailsEntity


@Database(
    entities = [UserEntity::class, TutorEntity::class, ProfileEntity::class, TutorDetailsEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun tutorListDao(): TutorListDao
    abstract fun tutorDetailsDao(): TutorDetailsDao
}
