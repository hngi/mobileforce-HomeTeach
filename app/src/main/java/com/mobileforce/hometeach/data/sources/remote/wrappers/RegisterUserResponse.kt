package com.mobileforce.hometeach.data.sources.remote.wrappers

/**
 * Authored by enyason
 */
class RegisterUserResponse(
    val token: String,
    val user: UserRemote,
    val errors: Errors?
)

data class Errors(val email: List<String>?, val phone_number: List<String>?)

