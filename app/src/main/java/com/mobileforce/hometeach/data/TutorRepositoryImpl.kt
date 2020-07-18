package com.mobileforce.hometeach.data

import com.mobileforce.hometeach.data.repository.TutorRepository
import com.mobileforce.hometeach.data.sources.DataSourceFactory
import com.mobileforce.hometeach.data.sources.remote.wrappers.TutorDetailsResponse

class TutorRepositoryImpl (private val dataSource: DataSourceFactory): TutorRepository {
    override suspend fun getTutorDetails(): TutorDetailsResponse {
        val user = dataSource.local().getSingleUser()
        return dataSource.remote().getTutorDetails(user.id.toDouble().toInt())
    }


}