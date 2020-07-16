package com.mobileforce.hometeach.ui.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mobileforce.hometeach.utils.AppConstants.USER_STUDENT
import com.mobileforce.hometeach.utils.AppConstants.USER_TUTOR
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.utils.PreferenceHelper
import com.mobileforce.hometeach.data.sources.remote.Params
import kotlinx.coroutines.launch
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserRemote
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.asLiveData
import kotlinx.coroutines.isActive


class SignInViewModel(
    private val userRepository: UserRepository,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {

    //Live data goes here

    private val _reset = MutableLiveData<Result<Nothing>>()
    val reset: LiveData<Result<Nothing>>
        get() = _reset

    private val _signIn = MutableLiveData<Result<Nothing>>()
    val signIn = _signIn.asLiveData()

    fun signIn(params: Params.SignIn) {
        _signIn.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.login(params)

                with(response.profile.user) {
                    val user = User(
                        token = response.token,
                        email = email,
                        phoneNumber = phoneNumber,
                        fullName = fullName,
                        isTutor = isTutor,
                        isActive = isActive,
                        id = id
                    )
                    userRepository.saveUser(user).also {
                        //save user type and ID to shared pref
                        if (isTutor) {
                            preferenceHelper.apply {
                                userType = USER_TUTOR
                                userId = user.id
                            }
                        } else {
                            preferenceHelper.apply {
                                userType = USER_STUDENT
                                userId = user.id
                            }
                        }
                        preferenceHelper.isLoggedIn = true

                    }
                }

                _signIn.postValue(Result.Success())



            } catch (error: Throwable) {
                _signIn.postValue(Result.Error(error))
            }

        }
    }

    fun resetPassword(params: Params.PasswordReset) {
        viewModelScope.launch {
            try {
                val emailResponse = userRepository.passwordReset(params)
                if (emailResponse.status == "OK") {
                    _reset.postValue(Result.Success())
                    Log.d("api", emailResponse.status)
                } else {
                    Log.d("api", emailResponse.status)
                }

            } catch (error: Throwable) {
                _reset.postValue(Result.Error(error))

            }
        }
    }

}