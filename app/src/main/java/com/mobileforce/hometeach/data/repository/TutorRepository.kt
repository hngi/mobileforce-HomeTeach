package com.mobileforce.hometeach.data.repository

import com.mobileforce.hometeach.data.sources.remote.wrappers.TutorDetailsResponse

interface TutorRepository {
    suspend fun getTutorDetails(): TutorDetailsResponse
}