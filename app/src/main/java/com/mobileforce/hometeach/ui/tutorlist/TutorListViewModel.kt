package com.mobileforce.hometeach.ui.tutorlist

import android.util.Log
import androidx.lifecycle.*
import com.mobileforce.hometeach.data.model.TutorEntity
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.models.TutorModel
import com.mobileforce.hometeach.utils.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Created by Mayokun Adeniyi on 14/07/2020.
 */

class TutorListViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    //List of all tutors
    private val _tutorList = MutableLiveData<Result<List<TutorModel>>>()
    val tutorList = _tutorList.asLiveData()

    private val _serviceApproved = MutableLiveData<Result<String>>()
    val serviceApproved = _serviceApproved.asLiveData()

    //Selected Tutor Id
    private var selectedId: String? = null
    private var userEntity: UserEntity? = null


    /**
     * This function fetches a fresh list of [TutorModel] from the remote source.
     * This is called also when the user swipes down on the screen.
     */
    fun refreshTutorList() {
        _tutorList.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.getTutorList()
                if (response.isSuccessful) {
                    val tutorListResponse = response.body()
                    val listOfTutors = tutorListResponse?.map {
                        it.toDomainModel()
                    }
                    _tutorList.postValue(Result.Success(listOfTutors))
                    userRepository.clearTutorListDb()
                    userRepository.saveTutorList(listOfTutors!!.map { it.toDbEntity() })

                } else {
                    _tutorList.postValue(Result.Success(null))
                }
            } catch (exception: Exception) {
                _tutorList.postValue(Result.Error(exception))
            }
        }
    }

    /**
     * Get the current user from the db
     */
    fun getUser() = liveData {
        emit(userRepository.getUser().value)
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
                if (userEntity != null && selectedId != null) {
                    val requestServiceParams = Params.RequestTutorService(
                        student_id = userEntity?.id!!,
                        tutor_id = selectedId!!,
                        from_hour = dialogData.fromHour,
                        from_minute = dialogData.fromMinute,
                        to_hour = dialogData.toHour,
                        to_minute = dialogData.toMinute,
                        dates = dialogData.dates
                    )
                    val response = userRepository.requestTutorService(requestServiceParams)
                    if (response.isSuccessful) {
                        val result = response.body()?.message
                        _serviceApproved.postValue(Result.Success(result))
                    }
                }
            } catch (exception: Exception) {
                _serviceApproved.postValue(Result.Error(exception))
            }
        }
    }

    fun searchForTutor(query: String): LiveData<List<TutorEntity>> {
        return userRepository.searchTutor(query)
    }

    fun setUser(result: UserEntity) {
        userEntity = result
    }

    fun setTutorId(tutorId: String) {
        selectedId = tutorId
    }
}


