package com.mobileforce.hometeach.models


data class Classes(
    val _id: Int,
    var tutor: List<Tutors>,
    var student: List<Students>,
    var subject: String,
    var description: String,
    var timeStart: Double,
    var hours: Int,
    var price: Double,
    var status: String
)

