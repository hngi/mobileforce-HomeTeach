package com.mobileforce.hometeach.models

data class Tutors(
    var Id : String,
    var name: String,
    var email: String,
    var password: String,
    var classes: Int,
    var rating: Int,
    var balance: Double,
    var subjects: List<Subjects>
)