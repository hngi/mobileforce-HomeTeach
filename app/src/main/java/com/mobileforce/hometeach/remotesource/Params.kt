package com.mobileforce.hometeach.remotesource

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
}