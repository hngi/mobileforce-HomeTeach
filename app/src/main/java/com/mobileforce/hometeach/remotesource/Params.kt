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
        val fullName: String,
        val isTutor: Boolean,
        val number: String
    )
}