package com.mobileforce.hometeach.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.TutorRepository
import com.mobileforce.hometeach.data.sources.remote.wrappers.TutorDetailsResponse
import com.mobileforce.hometeach.utils.Result
import kotlinx.coroutines.launch

class ProfileViewModel(private val tutorRepository: TutorRepository) : ViewModel()  {

    private val _getTutorDetails = MutableLiveData<Result<TutorDetailsResponse>>()
    val getTutorDetails: LiveData<Result<TutorDetailsResponse>> = _getTutorDetails


    fun getTutorDetails() {
        _getTutorDetails.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = tutorRepository.getTutorDetails()
                _getTutorDetails.postValue(Result.Success(response))
            } catch (error: Throwable) {
                _getTutorDetails.postValue(Result.Error(error))
            }
        }

    }

}