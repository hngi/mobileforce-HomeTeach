package com.mobileforce.hometeach.data.sources.remote.wrappers

import com.google.gson.annotations.SerializedName

/**
 * Created by Mayokun Adeniyi on 15/07/2020.
 */

data class TutorListResponse(
    val user: TutorUserModel,
    val profile_pic: String,
    @SerializedName("desc")
    val description: String,
    val hourly_rate: String,
    @SerializedName("major_course")
    val subjects: String,
    val rating: RatingModel
)


data class TutorUserModel(
    val id: String,
    val full_name: String
)

data class RatingModel(
    val rating: Int,
    val count: Int
)