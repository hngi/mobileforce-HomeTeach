package com.mobileforce.hometeach.models

import com.mobileforce.hometeach.data.sources.local.entities.TutorDetailsEntity
import com.mobileforce.hometeach.data.sources.remote.wrappers.TutorDetailsResponse


/**
 * Created by Mayokun Adeniyi on 23/07/2020.
 */

data class TutorDetailsForUserModel(
    val id: Int,
    val stringId: String,
    val full_name: String,
    val profile_pic: String,
    val hourly_rate: String,
    val desc: String,
    val field: String,
    val major_course: String,
    val other_courses: String,
    val state: String,
    val address: String,
    val user_url: String,
    val rating: Float,
    val rating_count: Int,
    val credentials: String,
    val videoUrl: String
)

fun TutorDetailsResponse.toDomainModel() = TutorDetailsForUserModel(
    this.id,
    this.user.id,
    this.user.full_name,
    this.profile_pic,
    this.hourly_rate,
    this.desc ?: "",
    this.field ?: "",
    this.major_course ?: "",
    this.other_courses ?: "",
    this.state ?: "",
    this.address ?: "",
    this.user_url,
    this.rating.rating ?: 0.0f,
    this.rating.count ?: 0,
    this.credentials ?: "",
    this.video ?: ""
)

fun TutorDetailsEntity.toDomainModel() = TutorDetailsForUserModel(
    this.id,
    this.stringId ?: "",
    this.full_name ?: "",
    this.profile_pic ?: "",
    this.hourly_rate ?: "",
    this.desc ?: "",
    this.field ?: "",
    this.major_course ?: "",
    this.other_courses ?: "",
    this.state ?: "",
    this.address ?: "",
    this.user_url ?: "",
    this.rating ?: 0.0f,
    this.rating_count ?: 0,
    this.credentials ?: "",
    this.videoUrl ?: ""
)

fun TutorDetailsResponse.toEntity() = TutorDetailsEntity(
    this.id,
    this.user.id,
    this.user.full_name,
    this.profile_pic,
    this.hourly_rate,
    this.desc ?: "",
    this.field ?: "",
    this.major_course ?: "",
    this.other_courses ?: "",
    this.state ?: "",
    this.address ?: "",
    this.user_url,
    this.rating.rating ?: 0.0f,
    this.rating.count ?: 0,
    this.credentials ?: "",
    this.video ?: ""
)