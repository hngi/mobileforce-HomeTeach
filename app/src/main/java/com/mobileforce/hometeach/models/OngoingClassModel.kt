package com.mobileforce.hometeach.models

data class OngoingClassModel(
    val subject: String,
    val date: String,
    val time: String,
    val tutorName: String,
    val tutorImage:String,
    val progress: Int,
    val tutorSubject: String) {
}

data class OngoingClassModelTutor(val subject: String,
                                  val date: String,
                                  val time: String,
                                  val tutorName: String,
                                  val tutorImage:String,
                                  val progress: Int,
                                  val mutualLesson: Int)