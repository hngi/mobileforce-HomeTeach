package com.mobileforce.hometeach.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobileforce.hometeach.data.sources.local.entities.TutorDetailsEntity

/**
 * Created by Mayokun Adeniyi on 23/07/2020.
 */

@Dao
interface TutorDetailsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTutorDetails(tutorDetails: TutorDetailsEntity)

    @Query("SELECT * FROM tutor_details WHERE id = :id LIMIT 1")
    suspend fun getTutorDetails(id: Int): TutorDetailsEntity?


    @Query("DELETE FROM tutor_details")
    suspend fun clearDb()

}