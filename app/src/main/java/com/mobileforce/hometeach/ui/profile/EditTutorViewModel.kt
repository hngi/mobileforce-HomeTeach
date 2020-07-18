package com.mobileforce.hometeach.ui.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
<<<<<<< HEAD
import com.mobileforce.hometeach.data.repository.TutorRepository
=======
import com.mobileforce.hometeach.data.repo.TutorRepository
>>>>>>> d4cb0fae9367a2886e0ddf61d2cb6374c803d499
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.InputStream

//import okhttp3.RequestBody.Companion.asRequestBody
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody.Companion.toRequestBody


class EditTutorViewModel(private val tutorRepository: TutorRepository) : ViewModel() {

    var success:Boolean = false


    fun uploadTutorMedia(Iprofile_pic: InputStream, credentials: InputStream, video: InputStream) {
        viewModelScope.launch {

            //IMAGE
            val image = MultipartBody.Part.createFormData(
                "profile_pic", "myPic", RequestBody.create(
                    MediaType.parse("image/*"),
                    Iprofile_pic.readBytes()))



            //Credentials

            val pdf = MultipartBody.Part.createFormData(
                "credentials", "myCredential", RequestBody.create(
                    MediaType.parse("application/pdf"),
                    credentials.readBytes()))


//VIDEO

            val Video = MultipartBody.Part.createFormData(
                "video", "myVideo", RequestBody.create(
                    MediaType.parse("video/*"),
                    video.readBytes()))


           val id = tutorRepository.getId()
            val Id: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), id)

            try {
                val response = tutorRepository.uploadTutorMedia(Id, image, pdf, Video)
                success = response.isSuccessful
            } catch (error: Exception) {
                // TODO

            }

        }


    }
}