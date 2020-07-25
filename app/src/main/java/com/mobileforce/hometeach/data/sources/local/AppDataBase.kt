package com.mobileforce.hometeach.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobileforce.hometeach.data.sources.local.dao.TutorDetailsDao
import com.mobileforce.hometeach.data.sources.local.dao.TutorListDao
import com.mobileforce.hometeach.data.sources.local.dao.UserDao
import com.mobileforce.hometeach.data.sources.local.dao.WalletDao
import com.mobileforce.hometeach.data.sources.local.entities.*


@Database(
    entities = [UserEntity::class, TutorEntity::class, ProfileEntity::class, TutorDetailsEntity::class, WalletEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun tutorListDao(): TutorListDao
    abstract fun tutorDetailsDao(): TutorDetailsDao
    abstract fun walletDao(): WalletDao
}
