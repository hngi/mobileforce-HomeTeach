package com.mobileforce.hometeach.ui.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repo.TutorRepository
import kotlinx.coroutines.launch
import java.io.File
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody


class EditTutorViewModel(private val tutorRepository: TutorRepository) : ViewModel() {


    fun uploadTutorMedia(
        id: String, profile_pic: Uri, credentials: Uri, video: Uri, context: Context
    ) {
        viewModelScope.launch {

            //IMAGE

            val imageInputStream = context.contentResolver.openInputStream(profile_pic)
            val tempFile = File.createTempFile("image", ".jpg")
            imageInputStream?.copyTo(tempFile.outputStream())
            val type = context.contentResolver.getType(profile_pic)
            val mediaType = type?.toMediaTypeOrNull()
            val requestFile = tempFile.asRequestBody(mediaType)
            val Profilepic =
                MultipartBody.Part.createFormData("profile_pic", tempFile.name, requestFile)


            //VIDEO

            val videoInputStream = context.contentResolver.openInputStream(video)
            val tempFile2 = File.createTempFile("video", ".mp4")
            videoInputStream?.copyTo(tempFile.outputStream())
            val type2 = context.contentResolver.getType(video)
            val mediaType2 = type2?.toMediaTypeOrNull()
            val requestFile2 = tempFile.asRequestBody(mediaType2)
            val Video = MultipartBody.Part.createFormData("video", tempFile2.name, requestFile2)


            //CREDENTIALS
            val credentialInputStream = context.contentResolver.openInputStream(credentials)
            val tempFile3 = File.createTempFile("Credential", ".pdf")
            credentialInputStream?.copyTo(tempFile.outputStream())
            val type3 = context.contentResolver.getType(credentials)
            val mediaType3 = type3?.toMediaTypeOrNull()
            val requestFile3 = tempFile.asRequestBody(mediaType3)
            val Credential =
                MultipartBody.Part.createFormData("credentials", tempFile.name, requestFile)


            //ID
            val Id = id.toRequestBody(MultipartBody.FORM)

            try {
                val response = tutorRepository.uploadTutorMedia(Id, Profilepic, Credential, Video)
                if (response.isSuccessful) {
                    Log.d("api", "CREDENTIAL UPLOADED SUCCESSFULLY")
                } else {
                    Log.d("api", response.body().toString())
                }
            } catch (error: Exception) {
                // TODO

            } finally {
                // Always delete temp file when upload complete
                tempFile.delete()
                tempFile2.delete()
                tempFile3.delete()
            }

        }


    }
}