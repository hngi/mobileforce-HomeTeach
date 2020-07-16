package com.mobileforce.hometeach.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repo.TutorRepository
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.wrappers.UploadResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart

class EditTutorViewModel(private val tutorRepository: TutorRepository) : ViewModel() {


    fun uploadTutorMedia(
        id: RequestBody, profile_pic: MultipartBody.Part,
        credentials: MultipartBody.Part,
        video: MultipartBody.Part
    ){


        viewModelScope.launch {

            try {
                val response = tutorRepository.uploadTutorMedia(id,profile_pic, credentials, video)

                if (response.isSuccessful)
                {
                    Log.d("test","DATAS UPLAODED SUCCESSFULLY")
                }
                else
                    Log.d("test",response.errorBody().toString())
            }
            catch (error: Exception){

            }

        }
    }


}