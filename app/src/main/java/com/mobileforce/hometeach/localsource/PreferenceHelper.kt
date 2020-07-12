package com.mobileforce.hometeach.localsource

import android.content.Context
import android.content.SharedPreferences
import com.mobileforce.hometeach.AppConstants.APP_SHARED_PREFERENCE
import com.mobileforce.hometeach.AppConstants.USER_TYPE

class PreferenceHelper constructor(context: Context) {

    private val pref: SharedPreferences =
        context.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = pref.getBoolean("isLoggedIn", false)
        set(value){
            pref.edit().putBoolean("isLoggedIn", value).apply()
        }

    var userType: String?
        get() = pref.getString(USER_TYPE, null)
        set(value) {
            pref.edit().putString(USER_TYPE, value).apply()
        }


}