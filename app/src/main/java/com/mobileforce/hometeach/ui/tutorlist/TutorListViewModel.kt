package com.mobileforce.hometeach.ui.tutorlist

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobileforce.hometeach.data.model.TutorEntity
import com.mobileforce.hometeach.data.model.UserEntity
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.models.TutorModel
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.asLiveData
import com.mobileforce.hometeach.utils.toDbEntity
import com.mobileforce.hometeach.utils.toDomainModel
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
    private var selectedTutor: TutorModel? = null
    private var userEntity: UserEntity? = null

    private val db = Firebase.firestore


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
                if (userEntity != null && selectedTutor != null) {
                    val requestServiceParams = Params.RequestTutorService(
                        student_id = userEntity?.id!!,
                        tutor_id = selectedTutor?.id!!,
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

                        //init chat connect

                        val connectID: String = selectedTutor!!.id + userEntity!!.id

                        val studentRef = db
                            .collection("user")
                            .document(userEntity?.id!!)
                            .collection("connect")
                            .document(selectedTutor!!.id)
                        val studentConnect = hashMapOf(
                            "id" to selectedTutor?.id,
                            "full_name" to selectedTutor?.full_name!!,
                            "connect_id" to connectID,
                            "last_message" to "You have a new connection",
                            "last_message_time" to FieldValue.serverTimestamp()
                        )


                        val tutorRef = db
                            .collection("user")
                            .document(selectedTutor?.id!!)
                            .collection("connect")
                            .document(userEntity!!.id)
                        val tutorConnect = hashMapOf(
                            "id" to userEntity?.id,
                            "full_name" to userEntity?.full_name,
                            "connect_id" to connectID,
                            "last_message" to "You have a new connection",
                            "last_message_time" to FieldValue.serverTimestamp()

                        )



                        db.runBatch { batch ->
                            batch.set(studentRef, studentConnect)

                            batch.set(tutorRef, tutorConnect)

                        }.addOnCompleteListener {

                            if (it.isSuccessful) {
                                println("batch write successful")
                            }
                        }

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

    fun setTutor(tutor: TutorModel) {
        selectedTutor = tutor
    }
}


