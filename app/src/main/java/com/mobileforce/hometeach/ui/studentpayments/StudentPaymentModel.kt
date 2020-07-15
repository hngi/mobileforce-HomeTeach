package com.mobileforce.hometeach.ui.studentpayments

class StudentPaymentModel (
    var payment: List<Payment>,
    var cards: List<UserCardDetailResponse>,
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

data class StudentCardModel(
    val _id: Int,
    var cards: List<UserCardDetailResponse>,
    val tutorName: String,
    val tutorImage:String,
    val balance: String
)

data class UserCardDetailResponse(
    val id:Int,
    val cardImage:Int,
    val cardNo:String,
    val status:Boolean
)
