package com.mobileforce.hometeach.local

import android.content.Context
import android.content.SharedPreferences
import com.mobileforce.hometeach.AppConstants.APP_SHARED_PREFERENCE
import com.mobileforce.hometeach.AppConstants.USER_TYPE

class PreferenceHelper constructor(context: Context) {

    private val pref: SharedPreferences =
        context.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE)


    var userType: String?
        get() = pref.getString(USER_TYPE, null)
        set(value) {
            pref.edit().putString(USER_TYPE, value).apply()
        }


}