package com.mobileforce.hometeach.data.repo

import com.mobileforce.hometeach.remotesource.wrappers.TutorDetailsResponse

interface TutorRepository {
    suspend fun getTutorDetails(): TutorDetailsResponse
}