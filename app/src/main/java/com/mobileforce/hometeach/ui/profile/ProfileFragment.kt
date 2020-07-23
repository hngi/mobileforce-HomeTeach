package com.mobileforce.hometeach.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.mobileforce.hometeach.utils.makeGone
import com.mobileforce.hometeach.utils.makeVisible
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
    private var credentialUrl: String? = null
    var tutorName: String = ""
    var imageUrl: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (pref.userType == USER_TUTOR) {
            bindingTutor = FragmentProfileBinding.inflate(layoutInflater)
            bindingTutor.root
        } else {
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
        } else {
            setUpProfileForStudent()
            bindingStudent.toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setUpProfileForTutor() {
        bindingTutor.editButton.setOnClickListener {
            val bundle = bundleOf("imageUrl" to imageUrl, "tutorName" to tutorName)
            navController.navigate(R.id.editTutorProfileFragment, bundle)
        }
        viewModel.getUserProfile()

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                bindingTutor.tutorName.text = user.full_name
                bindingTutor.tutorNameProfile.text = user.full_name
                tutorName = user.full_name

            }
        })
        viewModel.profile.observe(viewLifecycleOwner, Observer { profile ->

            profile?.let {

                Picasso.get().load(profile.profile_pic).transform(CircleTransform())
                    .error(R.drawable.profile_image).into(bindingTutor.tutorImage)
                imageUrl = profile.profile_pic.toString()
                bindingTutor.teachersRatingBar.rating = profile.rating ?: 0.0f
                profile.hourly_rate?.let {
                    bindingTutor.AmountTv.text = String.format("%s/hr", it)
                }

                if (profile.desc== "" )
                {
                    bindingTutor.descriptionText.visibility =View.INVISIBLE
                    bindingTutor.TutorDescriptionDetailCard.visibility = View.INVISIBLE
                    bindingTutor.tutorDesc.text = ""
                }


                bindingTutor.tutorDesc.text = profile.desc
                bindingTutor.tutorInterest.text = profile.other_courses
                credentialUrl = profile.credentials
                bindingTutor.TutorSubject.text =
                    if (!profile.major_course.isNullOrEmpty()) profile.major_course + " Tutor" else ""
                val videoUrl =
                    if (!profile.videoUrl.isNullOrEmpty()) profile.videoUrl else "http://www.ebookfrenzy.com/android_book/movie.mp4"
                bindingTutor.tutorVideo.setVideoPath(videoUrl)
                mediaController = MediaController(context)
                bindingTutor.tutorVideo.setMediaController(mediaController)
                mediaController?.setAnchorView(bindingTutor.tutorVideo)
                bindingTutor.tutorVideo.start()

                profile.field?.let {
                    bindingTutor.tvField.text = it
                }

                if (credentialUrl.isNullOrEmpty()) bindingTutor.credentialGroup.makeGone()
                else bindingTutor.credentialGroup.makeVisible()

                if (profile.desc.isNullOrEmpty()) bindingTutor.groupDescription.makeGone()
                else bindingTutor.groupDescription.makeVisible()
            }


        })

        bindingTutor.PdfImage.setOnClickListener {

            credentialUrl?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(credentialUrl))
                startActivity(intent)
            }

        }

    }

    private fun setUpProfileForStudent() {
        viewModel.getUserProfile()

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            bindingStudent.ProfileName.text = user.full_name
            bindingStudent.studentName.text = user.full_name
            bindingStudent.profileEmail.text = user.email
            bindingStudent.profileNumber.text = user.phone_number
        })

        viewModel.profile.observe(viewLifecycleOwner, Observer { profile ->

            bindingStudent.descriptionText.text = profile.desc
            Picasso.get().load(profile.profile_pic).transform(CircleTransform())
                .error(R.drawable.profile_image).into(bindingStudent.ProfileImage)

        })

    }

}