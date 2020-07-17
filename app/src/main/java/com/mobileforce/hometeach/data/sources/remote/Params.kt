package com.mobileforce.hometeach.data.sources.remote

/**
 * Authored by enyason
 */

class Params {

    data class SignIn(
        val email: String,
        val password: String
    )

    data class SignUp(
        val email: String,
        val password: String,
        val full_name: String,
        val is_tutor: Boolean,
        val phone_number: String,
        val organization_email: String = "akinsolaademolatemitope@gmail.com"

    )


    data class PasswordReset(
        val email: String
    )

    data class EditTutorProfile(
        val email: String,
        val full_name: String,
        val desc: String,
        val field: String,
        val major_course: String,
        val other_courses: String,
        val state: String,
        val address: String
    )

    data class CardDetails(
        val card_number: String,
        val card_cvc: String,
        val expiry_month: Int,
        val expiry_year: Int
    )

    data class RequestTutorService(
        val requester_id: String,
        val tutor_id: String,
        val from_hour: String,
        val from_minute: String,
        val to_hour: String,
        val to_minute: String,
        val dates: List<String>
    )
}