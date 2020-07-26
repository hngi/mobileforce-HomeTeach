package com.mobileforce.hometeach.ui.classes.tutor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.TutorRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.models.TutorRequestDataModel
import com.mobileforce.hometeach.models.TutorUpcomingDataModel
import kotlinx.coroutines.launch

class TutorUpcomingViewModel(private val tutorRepository: TutorRepository): ViewModel() {
    private val _tutorUpcoming= MutableLiveData<TutorUpcomingDataModel>()
    val tutorUpcoming: LiveData<TutorUpcomingDataModel> = _tutorUpcoming

    fun getTutorSchedules(){
        viewModelScope.launch {
            try {
                val user = tutorRepository.getTutorId()
                val id = user.id
                val tutor_id = Params.TutorClassesRequest(id)
                Log.d("dev",tutor_id.toString())
                val response = tutorRepository.getTutorClasses(tutor_id)
                _tutorUpcoming.postValue(response)
            }
            catch (error: Throwable){
//                _tutorUpcoming.postValue(Result.failure<error>())

            }

        }
    }

}