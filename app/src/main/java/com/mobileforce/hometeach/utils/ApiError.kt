package com.mobileforce.hometeach.utils

import com.google.gson.Gson
import retrofit2.HttpException

class ApiError(private val throwable: Throwable?) : Throwable() {

    override var message: String = UNKNOWN_ERROR

    init {
        errorResponse()
    }


    private fun errorResponse() {

        when (throwable) {


            is HttpException -> {

                val response = throwable.response()

                message = when (response!!.code()) {

                    400 -> {
                        val json = response.errorBody()?.string()!!
                        json

                    }
                    else -> UNKNOWN_ERROR
                }


            }

            else -> {
                message = UNKNOWN_ERROR
            }


        }

    }

    companion object {

        const val UNKNOWN_ERROR = "Some Error Occurred"

        val gson = Gson()

    }


}

