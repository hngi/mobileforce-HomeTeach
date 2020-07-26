package com.mobileforce.hometeach.models

data class TutorUpcomingDataModel(
    val schedules: List<Schedule>,
    val tutor_id: String
)