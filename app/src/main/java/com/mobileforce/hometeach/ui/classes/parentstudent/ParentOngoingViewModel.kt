package com.mobileforce.hometeach.ui.classes.parentstudent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserClassRequestResponse
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.asLiveData
import kotlinx.coroutines.launch

class ParentOngoingViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _studentClassRequest = MutableLiveData<Result<UserClassRequestResponse>>()
    val studentClassRequest = _studentClassRequest.asLiveData()

    /**
     * This function fetches an instance of [UserClassRequestResponse] from the remote source.
     * From which we can get the details about user class requests
     */
    fun getStudentClassRequest() {
        _studentClassRequest.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.getStudentClassRequest()
                _studentClassRequest.postValue(Result.Success(response))
            } catch (error: Throwable) {
                _studentClassRequest.postValue(Result.Error(error))
            }
        }
    }
}