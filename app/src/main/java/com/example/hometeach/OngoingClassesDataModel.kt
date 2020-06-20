package com.example.hometeach

data class OngoingClassesDataModel(
    var subject: String,
    var completion: String,
    var progress: Int,
    var teachers_image: String,
    var teachers_name: String,
    var teachers_subject: String
)