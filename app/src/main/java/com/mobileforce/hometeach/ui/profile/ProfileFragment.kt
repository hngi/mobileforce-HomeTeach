package com.mobileforce.hometeach.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.CircleTransform
import com.mobileforce.hometeach.databinding.FragmentProfileBinding
import com.mobileforce.hometeach.databinding.FragmentStudentProfileBinding
import com.mobileforce.hometeach.utils.AppConstants.USER_TUTOR
import com.mobileforce.hometeach.utils.PreferenceHelper
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.snack
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Authored by enyason
 */
class ProfileFragment : Fragment() {
    lateinit var navController: NavController
    private var mediaController: MediaController? = null
    private lateinit var bindingTutor: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var bindingStudent: FragmentStudentProfileBinding
    private val pref: PreferenceHelper by inject()
    var url:String = ""
     var  tutorName:String = ""
    var imageUrl:String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (pref.userType == USER_TUTOR){
            bindingTutor = FragmentProfileBinding.inflate(layoutInflater)
            bindingTutor.root
        }else{
            bindingStudent = FragmentStudentProfileBinding.inflate(layoutInflater)
            bindingStudent.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        if (pref.userType == USER_TUTOR) {
            setUpProfileForTutor()
            bindingTutor.toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }else{
            setUpProfileForUser()
            bindingStudent.toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setUpProfileForTutor(){
        bindingTutor.editButton.setOnClickListener {
            val bundle = bundleOf("imageUrl" to imageUrl,"tutorName" to tutorName)
            navController.navigate(R.id.editTutorProfileFragment, bundle)
        }
        viewModel.getTutorDetails()
        viewModel.getTutorDetails.observe(viewLifecycleOwner, Observer { result ->
            Log.d("Result", result.toString())
            when (result) {
                Result.Loading -> {

                }
                is Result.Success -> {
                    bindingTutor.tutorName.text = result.data?.user?.full_name
                    bindingTutor.tutorNameProfile.text= result.data?.user?.full_name
                    tutorName = result.data?.user?.full_name.toString()
                    Picasso.get().load(result.data?.profile_pic).transform(CircleTransform()).error(R.drawable.profile_image).into(bindingTutor.tutorImage)
                    imageUrl = result.data?.profile_pic.toString()
                    bindingTutor.teachersRatingBar.rating = result.data?.rating?.count?.toFloat()!!
                    bindingTutor.AmountTv.text= result.data?.hourly_rate + "/hr"
                    bindingTutor.tutorDesc.text = result.data?.desc
                    bindingTutor.tutorInterest.text = result.data?.other_courses
                    url = result.data?.credentials
                    bindingTutor.TutorSubject.text = if (!result.data?.major_course.isNullOrEmpty()) result.data?.major_course +" Tutor" else ""
                    var  videourl = if (!result.data?.video.isNullOrEmpty()) result.data?.video  else "http://www.ebookfrenzy.com/android_book/movie.mp4"
                    bindingTutor.tutorVideo.setVideoPath(videourl)
                    mediaController = MediaController(context)
                    mediaController?.setAnchorView(bindingTutor.tutorVideo)
                    bindingTutor.tutorVideo.setMediaController(mediaController)
                    bindingTutor.tutorVideo.start()
                }

                is Result.Error -> {

                    bindingTutor.profileLayout.snack(message = result.exception.localizedMessage)
                }
            }

        })

        bindingTutor.PdfImage.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent);
        }

    }

    private fun setUpProfileForUser(){
        viewModel.getUserProfile()
        viewModel.getStudentProfile.observe(viewLifecycleOwner, Observer { result ->
            Log.d("Result", result.toString())
            when (result) {
                Result.Loading -> {

                }
                is Result.Success -> {
                    bindingStudent.ProfileName.text = result.data?.user?.fullName
                    bindingStudent.studentName.text = result.data?.user?.fullName
                    bindingStudent.descriptionText.text = result.data?.desc
                    bindingStudent.profileEmail.text = result.data?.user?.email
                    bindingStudent.profileNumber.text = result.data?.user?.phoneNumber
                    Picasso.get().load(result.data?.profile_pic).transform(CircleTransform()).error(R.drawable.profile_image).into(bindingStudent.ProfileImage)
                }

                is Result.Error -> {

                    bindingStudent.studentProfileLayout.snack(message = result.exception.localizedMessage)
                }
            }

        })

    }

}