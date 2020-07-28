package com.mobileforce.hometeach.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.utils.AppConstants.COLLECTION_USER
import com.mobileforce.hometeach.utils.Result
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _signUp = MutableLiveData<Result<Nothing>>()
    val signUp: LiveData<Result<Nothing>> = _signUp

    val credentialsError = MutableLiveData<CredentialsError>()

    private val db = Firebase.firestore

    fun signUp(params: Params.SignUp) {
        _signUp.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.register(params)

                if (response.errors != null) {
                    response.errors.let {

                        it.email?.let email@{ list ->

                            credentialsError.postValue(CredentialsError(ErrorField.EMAIL, list[0]))
                            return@email
                        }

                        it.phone_number?.let phone@{ list ->
                            credentialsError.postValue(
                                CredentialsError(
                                    ErrorField.PHONE_NUMBER,
                                    list[0]
                                )
                            )
                            return@phone
                        }

                    }
                } else {

                    _signUp.postValue(Result.Success())

                    //register user to firebase

                    with(response.user) {
                        val user = User(
                            token = response.token,
                            email = email,
                            phoneNumber = phoneNumber,
                            fullName = fullName,
                            isTutor = isTutor,
                            isActive = isActive,
                            id = id
                        )
                        db.collection(COLLECTION_USER)
                            .document(id)
                            .set(user)

                    }

                }

            } catch (error: Throwable) {
                _signUp.postValue(Result.Error(error))
            }
        }

    }

    data class CredentialsError(val error: ErrorField, val message: String)

    enum class ErrorField {
        EMAIL,
        PHONE_NUMBER
    }
}