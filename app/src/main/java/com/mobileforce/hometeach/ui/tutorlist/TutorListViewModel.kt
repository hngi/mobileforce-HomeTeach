package com.mobileforce.hometeach.ui.tutorlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.model.TutorEntity
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.TutorServiceRequestResponse
import com.mobileforce.hometeach.models.TutorAllModel
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.asLiveData
import com.mobileforce.hometeach.utils.toDbEntity
import com.mobileforce.hometeach.utils.toDomainModel
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by Mayokun Adeniyi on 14/07/2020.
 */

class TutorListViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    //List of all tutors
    private val _tutorList = MutableLiveData<Result<List<TutorAllModel>>>()
    val tutorList = _tutorList.asLiveData()

    private val _serviceApproved = MutableLiveData<Result<TutorServiceRequestResponse>>()
    val serviceApproved = _serviceApproved.asLiveData()

    //Selected Tutor Id
    private var selectedId: String? = null

    fun setTutorId(tutorId: String) {
        selectedId = tutorId
    }

    /**
     * This function fetches a fresh list of [TutorAllModel] from the remote source.
     * This is called also when the user swipes down on the screen.
     */
    fun refreshTutorList() {
        _tutorList.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.getTutorList()
                if (response.isSuccessful) {
                    val tutorListResponse = response.body()
                    if (tutorListResponse != null) {
                        val listOfTutors = response.body()?.map {
                            TutorAllModel(
                                id = it.user.id,
                                full_name = it.user.full_name,
                                profile_pic = it.profile_pic,
                                description = it.description,
                                tutorSubject = it.subjects,
                                hourly_rate = it.hourly_rate.toInt(),
                                rating = it.rating.rating.toString()
                            )
                        }
                        if (listOfTutors != null) {
                            _tutorList.postValue(Result.Success(listOfTutors))
                            userRepository.clearTutorListDb()
                            userRepository.saveTutorList(listOfTutors.map { it.toDbEntity() })
                        }
                    }
                } else {
                    _tutorList.postValue(Result.Success(null))
                }
            } catch (exception: Exception) {
                _tutorList.postValue(Result.Error(exception))
            }
        }
    }

    /**
     * This initially attempts to get data from the cache. If this is empty, it would
     * fetch from the remote source.
     */
    fun getTutorList() {
        viewModelScope.launch {
            val listTutors = userRepository.getTutorListDb()
            if (listTutors.isNullOrEmpty()) {
                refreshTutorList()
            } else {
                _tutorList.postValue(Result.Success(listTutors.map { it.toDomainModel() }))
            }
        }
    }


    /**
     * This function helps the student/parent schedule classes i.e. request the services
     * of a tutor.
     * @param dialogData this contains the information of the schedule the student/parent
     * wants to setup with a tutor
     */
    fun getTutorService(dialogData: DialogData) {
        _serviceApproved.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val user = userRepository.getUser().value
                if (user != null && selectedId != null) {
                    val requestServiceParams = Params.RequestTutorService(
                        requester_id = user.id,
                        tutor_id = selectedId!!,
                        from_hour = dialogData.fromHour,
                        from_minute = dialogData.fromMinute,
                        to_hour = dialogData.toHour,
                        to_minute = dialogData.toMinute,
                        dates = dialogData.dates
                    )
                    val response = userRepository.requestTutorService(requestServiceParams)
                    if (response.isSuccessful) {
                        _serviceApproved.postValue(Result.Success(response.body()))
                    }
                }
            } catch (exception: Exception) {
                _serviceApproved.postValue(Result.Error(exception))
            }
        }
    }

    fun searchForTutor(query: String): LiveData<List<TutorEntity>>{
        return userRepository.searchTutor(query)
    }
}


