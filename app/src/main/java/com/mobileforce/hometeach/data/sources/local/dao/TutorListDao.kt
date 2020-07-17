package com.mobileforce.hometeach.data.sources.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobileforce.hometeach.data.model.TutorEntity

/**
 * Created by Mayokun Adeniyi on 17/07/2020.
 */

interface TutorListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTutors(listOfTutors: List<TutorEntity>)

    @Query("DELETE FROM tutors")
    suspend fun clearDatabase()

    @Query("SELECT * FROM tutors")
    suspend fun getTutors(): List<TutorEntity>

    @Query("SELECT * FROM tutors WHERE full_name LIKE :query")
    fun getSearchTutor(query: String): LiveData<List<TutorEntity>>
}