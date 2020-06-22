package com.mobileforce.hometeach.models

data class Students (
    var Id : Long,
    var name: String,
    var email: String,
    var password: String,
    var subjects: List<Subjects>
)
