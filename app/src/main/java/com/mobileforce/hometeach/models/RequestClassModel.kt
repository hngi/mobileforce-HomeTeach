package com.mobileforce.hometeach.models

/**
 * Created by Mayokun Adeniyi on 28/06/2020.
 */

data class RequestClassModel(
    val id: Int,
    val subjectName: String,
    val price: Double,
    val tutorName: String,
    val date: String,
    val time: String,
    val grade: Int,
    val status: String,
    val showButtons: Boolean
)