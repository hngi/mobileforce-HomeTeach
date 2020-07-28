package com.mobileforce.hometeach.ui.classes.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.TutorRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.StudentRequestResponse
import kotlinx.coroutines.launch

class StudentRequestViewModel(private val tutorRepository: TutorRepository) : ViewModel() {
    private val _studentRequest = MutableLiveData<Result<StudentRequestResponse>>()
    val studentRequest: LiveData<Result<StudentRequestResponse>> = _studentRequest


    fun grantStudentRequest(params: Params.StudentRequest) {
        viewModelScope.launch {
            try {
                val response = tutorRepository.grantStudentRequest(params)
                _studentRequest.postValue(Result.success(response))
            } catch (error: Throwable) {
                _studentRequest.postValue(Result.failure(error))
            }

        }

    }


}