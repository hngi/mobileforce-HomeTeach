package com.mobileforce.hometeach.ui.classes.parentstudent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserClassesResponse
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.asLiveData
import kotlinx.coroutines.launch

class ParentUpcomingViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _studentClasses = MutableLiveData<Result<UserClassesResponse>>()
    val studentClasses = _studentClasses.asLiveData()

    /**
     * This function fetches an instance of [UserClassesResponse] from the remote source.
     * From which we can get the details about user class requests
     */
    fun getStudentClasses() {
        _studentClasses.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.getStudentClasses()
                _studentClasses.postValue(Result.Success(response))
            } catch (error: Throwable) {
                _studentClasses.postValue(Result.Error(error))
            }
        }
    }
}