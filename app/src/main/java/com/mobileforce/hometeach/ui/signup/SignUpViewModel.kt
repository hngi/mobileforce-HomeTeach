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

    val db = Firebase.firestore

    fun signUp(params: Params.SignUp) {
        _signUp.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.register(params)

                response.errors?.let {

                    it.email?.let email@{
                        _signUp.postValue(Result.Error(Throwable(it[0])))

                        return@email
                    }

                } ?: kotlin.run {
                    _signUp.postValue(Result.Success())

                    //register user to firebase

                    with(response.userRemote){
                        val user = User(
                            token = response.token,
                            email = email,
                            phoneNumber = phoneNumber,
                            fullName = fullName,
                            isTutor = isTutor,
                            isActive = isActive,
                            id = id.toString()
                        )
                        db.collection(COLLECTION_USER)
                            .document(user.id)
                            .set(user)

                    }


                }

            } catch (error: Throwable) {
                _signUp.postValue(Result.Error(error))
            }
        }

    }
}