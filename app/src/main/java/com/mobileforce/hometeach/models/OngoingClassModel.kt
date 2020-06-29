package com.mobileforce.hometeach.models

data class OngoingClassModel(
    val id: Int,
    val subject: String,
    val date: String,
    val time: String,
    val tutorName: String,
    val tutorImage:String,
    val progress: Int,
    val tutorSubject: String) {
}

data class OngoingClassModelTutor(val id: Int,
                                  val subject: String,
                                  val date: String,
                                  val time: String,
                                  val tutorName: String,
                                  val tutorImage:String,
                                  val progress: Int,
                                  val mutualLesson: Int)