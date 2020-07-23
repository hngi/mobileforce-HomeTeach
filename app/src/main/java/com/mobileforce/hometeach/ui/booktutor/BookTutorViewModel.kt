package com.mobileforce.hometeach.ui.booktutor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobileforce.hometeach.data.sources.local.entities.UserEntity
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.models.TutorModel
import com.mobileforce.hometeach.ui.tutorlist.DialogData
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.asLiveData
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by Mayokun Adeniyi on 23/07/2020.
 */

class BookTutorViewModel (val repository: UserRepository): ViewModel(){

    private val _serviceApproved = MutableLiveData<Result<String>>()
    val serviceApproved = _serviceApproved.asLiveData()

    private val db = Firebase.firestore

    private var userEntity: UserEntity? = null
    //Selected Tutor Id
    private var selectedTutor: TutorModel? = null



    /**
     * Get the current user from the db
     */
    fun getUser() = repository.getUser()

    /**
     * This function helps the student/parent schedule classes i.e. request the services
     * of a tutor.
     * @param dialogData this contains the information of the schedule the student/parent
     * wants to setup with a tutor
     */
    fun getTutorService(dialogData: DialogData, subject: String, grade: String) {
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
                        dates = dialogData.dates,
                        subject = subject,
                        grade = grade
                    )
                    val response = repository.requestTutorService(requestServiceParams)
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

    fun setUser(result: UserEntity) {
        userEntity = result
    }

    fun setTutor(tutor: TutorModel) {
        selectedTutor = tutor
    }
}