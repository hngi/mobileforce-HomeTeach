package com.mobileforce.hometeach.ui.tutordetails

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentTutorDetailsBinding
import com.mobileforce.hometeach.utils.Result
import kotlinx.android.synthetic.main.request_layout.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class TutorDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTutorDetailsBinding
    private val viewModel: TutorDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar3.visibility = View.VISIBLE
        hideViews(true)
        val tutorDetailsFragmentArgs by navArgs<TutorDetailsFragmentArgs>()
        val tutorModel = tutorDetailsFragmentArgs.tutorModel
        viewModel.getTutorDetails(tutorModel.integerId)
        observeViewModels()
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.requestService.setOnClickListener {
            val action =
                TutorDetailsFragmentDirections.actionTutorDetailsFragmentToBookTutorFragment(
                    tutorModel
                )
            findNavController().navigate(action)
        }

        binding.viewAll.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(activity).inflate(R.layout.credentials_dialog, null)
            val mBuilder = activity?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)

            }

            val mAlertDialog = mBuilder?.show()
            mBuilder?.setTitle("Credentials")
            mDialogView.btn.setOnClickListener {
                mAlertDialog?.hide()
            }
        }


    }

    private fun observeViewModels() {
        viewModel.tutorDetails.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    unHideViews(true)
                    binding.progressBar3.visibility = View.GONE
                    val tutorDetails = result.data
                    setupVideo(tutorDetails?.videoUrl)
                    binding.tutorDetails = tutorDetails
                }
                is Result.Loading -> {
                    binding.progressBar3.visibility = View.VISIBLE
                    hideViews(true)
                }
                is Result.Error -> {
                    hideViews(true)
                    binding.progressBar3.visibility = View.GONE
                }
            }
        })
    }

    private fun setupVideo(videoUrl: String?) {
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.tutorVideo)
        binding.tutorVideo.setMediaController(mediaController)
        binding.tutorVideo.setVideoURI(Uri.parse(videoUrl))
        binding.tutorVideo.requestFocus()

        binding.tutorVideo.setOnPreparedListener {
            MediaPlayer.OnPreparedListener { mediaPlayer ->
                mediaPlayer?.setOnVideoSizeChangedListener { _: MediaPlayer, _: Int, _: Int ->
                    MediaPlayer.OnVideoSizeChangedListener { _, _, _ ->
                        binding.tutorVideo.setMediaController(mediaController)
                        mediaController.setAnchorView(binding.tutorVideo)
                    }
                }
            }
        }
    }

    private fun hideViews(state: Boolean) {
        if (state) {
            binding.apply {
                relativeLayout.visibility = View.GONE
                descriptionText.visibility = View.GONE
                requestService.visibility = View.GONE
                textView9.visibility = View.GONE
                viewAll.visibility = View.GONE
                img.visibility = View.GONE
                rahmanName.visibility = View.GONE
                mainCard.visibility = View.GONE
            }
        }
    }

    private fun unHideViews(state: Boolean) {
        if (state) {
            binding.apply {
                relativeLayout.visibility = View.VISIBLE
                descriptionText.visibility = View.VISIBLE
                requestService.visibility = View.VISIBLE
                textView9.visibility = View.VISIBLE
                viewAll.visibility = View.VISIBLE
                img.visibility = View.VISIBLE
                rahmanName.visibility = View.VISIBLE
                mainCard.visibility = View.VISIBLE
            }
        }
    }


}