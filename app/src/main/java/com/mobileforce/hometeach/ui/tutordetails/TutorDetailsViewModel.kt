package com.mobileforce.hometeach.ui.tutordetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.TutorRepository
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.models.TutorDetailsForUserModel
import com.mobileforce.hometeach.models.toDomainModel
import com.mobileforce.hometeach.models.toEntity
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.asLiveData
import kotlinx.coroutines.launch

/**
 * Created by Mayokun Adeniyi on 21/07/2020.
 */

class TutorDetailsViewModel(
    val repository: UserRepository,
    private val tutorRepository: TutorRepository
) : ViewModel() {

    private val _tutorDetails = MutableLiveData<Result<TutorDetailsForUserModel>>()
    val tutorDetails = _tutorDetails.asLiveData()


    fun refreshTutorDetails(tutorId: Int) {
        _tutorDetails.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = repository.getTutorDetailsForUser(tutorId)
                if (response.isSuccessful) {
                    val tutorDetailsResponse = response.body()
                    if (tutorDetailsResponse != null) {
                        _tutorDetails.postValue(Result.Success(tutorDetailsResponse.toDomainModel()))
                        repository.saveTutorDetailsForUserDb(tutorDetailsResponse.toEntity())

                    }
                } else {
                    _tutorDetails.postValue(Result.Error(Exception("Invalid data")))
                }
            } catch (exception: Exception) {
                _tutorDetails.postValue(Result.Error(exception))
            }
        }
    }

    fun getTutorDetails(tutorId: Int) {
        _tutorDetails.postValue(Result.Loading)
        viewModelScope.launch {
            val result = repository.getTutorDetailsForUserDb(tutorId)
            val profileCountParam =
                Params.TutorProfileVisitsCount(tutor_id = result!!.stringId!!)
            //fire and forget
            tutorRepository.updateProfileVisitsCount(profileCountParam)
            if (result == null) {
                refreshTutorDetails(tutorId)
            } else {
                _tutorDetails.postValue(Result.Success(result.toDomainModel()))
            }
        }
    }

    //call this function when tutor details has been successfully fetch
    private fun updateTutorProfileCount(param: Params.TutorProfileVisitsCount) =
        viewModelScope.launch {
            try {
                tutorRepository.updateProfileVisitsCount(param)
            } catch (e: Exception) {
            }

        }
}