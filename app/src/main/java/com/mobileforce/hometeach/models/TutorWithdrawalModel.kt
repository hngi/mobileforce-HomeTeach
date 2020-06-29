package com.mobileforce.hometeach.models

import androidx.recyclerview.widget.DiffUtil

data class TutorWithdrawalModel(
    val _id: Int,
    var payment: List<Payment>,
    val tutorName: String,
    val tutorImage:String,
    val balance: String
)
data class Payment(
    val id:Int,
    val dateTime:String,
    val amount:String,
    val status: String

)
data class MyBankModel(
    val _id: Int,
    var banks: List<MyBank>,
    val tutorName: String,
    val tutorImage:String,
    val balance: String
)
data class MyCardModel(
    val _id: Int,
    var cards: List<MyCard>,
    val tutorName: String,
    val tutorImage:String,
    val balance: String
)
data class MyBank(
    val id:Int,
    val bankName:String,
    val bankNo:String,
    val status:Boolean

)
data class MyCard(
    val id:Int,
    val cardImage:Int,
    val cardNo:String,
    val status:Boolean

)