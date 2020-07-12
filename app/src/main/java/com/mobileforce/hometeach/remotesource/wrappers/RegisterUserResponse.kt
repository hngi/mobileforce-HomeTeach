package com.mobileforce.hometeach.remotesource.wrappers

/**
 * Authored by enyason
 */
class RegisterUserResponse(
    val token: String,
    val userRemote: UserRemote,
    val errors: Errors?
)

data class Errors(val email: List<String>?)

