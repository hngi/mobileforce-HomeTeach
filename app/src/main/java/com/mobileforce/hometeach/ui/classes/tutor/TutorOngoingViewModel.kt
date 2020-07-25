package com.mobileforce.hometeach.ui.classes.tutor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.TutorRepository
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.models.TutorRequestDataModel
import kotlinx.coroutines.launch

class TutorOngoingViewModel(private val tutorRepository: TutorRepository): ViewModel() {
    private val _tutorOngoing= MutableLiveData<TutorRequestDataModel>()
    val tutorOngoing: LiveData<TutorRequestDataModel> = _tutorOngoing

    fun getTutorRequest(){
        viewModelScope.launch {
            try {
                val user = tutorRepository.getTutorId()
                val id = user.id
                val tutor_id = Params.TutorClassesRequest(id)
                Log.d("dev",tutor_id.toString())
                val response = tutorRepository.getTutorClasses(tutor_id)
                _tutorOngoing.postValue(response)
            }
            catch (error: Throwable){
//                _tutorRequest.postValue("Result.Error(error)")

            }

        }
    }

}