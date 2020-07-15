package com.mobileforce.hometeach.data.sources.remote.wrappers

/**
 * Authored by enyason
 */
class RegisterUserResponse(
    val token: String,
    val userRemote: UserRemote,
    val errors: Errors?
)

data class Errors(val email: List<String>?)

