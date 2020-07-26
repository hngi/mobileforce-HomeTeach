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

 class TutorRequestViewModel(private val tutorRepository: TutorRepository): ViewModel(){

    private val _tutorRequest = MutableLiveData<TutorRequestDataModel>()
    val tutorRequest: LiveData<TutorRequestDataModel> = _tutorRequest
     val tutorId =MutableLiveData<String>()



    fun getTutorRequest(){
        viewModelScope.launch {
            try {
                val user = tutorRepository.getTutorId()
                val id = user.id
                tutorId.postValue(id)
                val tutor_id = Params.TutorClassesRequest(id)
                Log.d("dev",tutor_id.toString())
                Log.d("dev","this is dummy text ")
                val response = tutorRepository.getTutorClassesRequest(tutor_id)
                _tutorRequest.postValue(response)

            }
            catch (error: Throwable){
//                _tutorRequest.postValue("Result.Error(error)")

            }

        }
    }



}
