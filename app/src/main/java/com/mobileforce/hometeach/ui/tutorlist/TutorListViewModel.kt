package com.mobileforce.hometeach.ui.tutorlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.utils.PreferenceHelper
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by Mayokun Adeniyi on 14/07/2020.
 */

class TutorListViewModel(
    private val userRepository: UserRepository,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {



    private fun getTutorList(){
        viewModelScope.launch {
            try {
               val response = userRepository.getTutorList()
            }catch (exception: Exception){

            }
        }
    }
}