package com.mobileforce.hometeach.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.databinding.FragmentEditTutorProfileBinding
import com.mobileforce.hometeach.utils.ApiError
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.loadImage
import com.mobileforce.hometeach.utils.toast
import com.squareup.picasso.Picasso
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.fragment_edit_tutor_profile.*
import kotlinx.android.synthetic.main.uploads.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.FileNotFoundException
import java.io.InputStream


/**
 * Created by MayorJay
 */
class EditTutorProfileFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var binding: FragmentEditTutorProfileBinding
    val REQUEST_CODE = 1001
    val REQUEST_CODE2 = 1005
    val REQUEST_CODE3 = 1009
    private lateinit var mDialogView: View
    private lateinit var mAlertDialog: AlertDialog

    private val viewModel: EditTutorViewModel by viewModel()
    private val listener by lazy {
        object : MaterialSpinner.OnItemSelectedListener {
            override fun onItemSelected(
                parent: MaterialSpinner,
                view: View?,
                position: Int,
                id: Long
            ) {
                parent.focusSearch(View.FOCUS_UP)?.requestFocus()
            }

            override fun onNothingSelected(parent: MaterialSpinner) {
                //("Not yet implemented")
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditTutorProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Inflates the Spinner displaying the tutor's field
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.fields_array,
            android.R.layout.simple_spinner_item
        ).let {
            it.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
            sp_select_field.apply {
                adapter = it
                onItemSelectedListener = listener
            }
        }

        binding.tutorName.text = arguments?.getString("tutorName").toString()
        val imageUrl = arguments?.getString("imageUrl").toString()
        Picasso.get().load(imageUrl).error(R.drawable.upload_pic).into(binding.profilePic)
        // Inflates the Spinner displaying the states of origin
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.origin_array,
            android.R.layout.simple_spinner_item
        ).let {
            it.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
            sp_select_origin.apply {
                adapter = it
                onItemSelectedListener = listener
            }
        }

        binding.profilePic.setOnClickListener {
            selectImage()


        }
        binding.tvUpload.setOnClickListener {
            selectVideo()

        }
        binding.upload.setOnClickListener {
            selectPdf()
        }
        binding.btSaveProfile.setOnClickListener {
            var selectedField = binding.spSelectField.selectedItem.toString()
            val majorCourses = binding.etCourseInput.text.toString()
            val otherCourses = binding.etOtherCourseInput.text.toString()
            val stateOfOrigin = binding.spSelectOrigin.selectedItem.toString()
            val address = binding.etAddressInput.text.toString()
            val rate = binding.etRateInput.text.toString()
            val description = binding.etDescription.text.toString()
            showDialog()
            mDialogView.progressBar.visibility = View.VISIBLE
            val data = Params.UpdateTutorProfile(
                selectedField,
                majorCourses,
                otherCourses,
                stateOfOrigin,
                address,
                rate,
                description
            )
            viewModel.updateTutorProfile(data)
            viewModel.updateTutorProfile.observe(viewLifecycleOwner, Observer { result ->
                Log.d("Result", result.toString())
                when (result) {
                    Result.Loading -> {

                    }
                    is Result.Success -> {
                        mDialogView.progressBar.visibility = View.INVISIBLE
                        mDialogView.successImage.visibility = View.VISIBLE
                        mAlertDialog.dismiss()
                        findNavController().navigate(R.id.profileFragment)
                    }
                    is Result.Error -> {
                        mAlertDialog.dismiss()
                        val message = ApiError(result.exception).message
                        toast(message)
                    }
                }
            })
        }

        viewModel.profile.observe(viewLifecycleOwner, Observer { profile ->
            profile?.let {
                binding.profilePic.loadImage(
                    profile.profile_pic,
                    placeholder = R.drawable.profile_image
                )

                profile.hourly_rate?.let {
                    binding.etRateInput.setText(it)
                }

                binding.etDescription.setText(profile.desc)
                binding.etOtherCourseInput.setText(profile.other_courses)
                binding.etCourseInput.setText(profile.major_course)
                binding.etDescription.setText(profile.desc)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            try {
                data?.let {
                    val inputStream: InputStream? =
                        context?.contentResolver?.openInputStream(it.data!!)
                    inputStream?.let { stream ->
                        showDialog()
                        mDialogView.progressBar.visibility = View.VISIBLE
                        viewModel.uploadPhoto(stream)
                        viewModel.uploadPhoto.observe(viewLifecycleOwner, Observer { result ->
                            Log.d("Result", result.toString())
                            when (result) {
                                Result.Loading -> {

                                }
                                is Result.Success -> {
                                    mDialogView.progressBar.visibility = View.INVISIBLE
                                    mDialogView.successImage.visibility = View.VISIBLE
                                }
                                is Result.Error -> {
                                    mAlertDialog?.dismiss()
                                    Toast.makeText(
                                        activity,
                                        "SORRY AN ERROR OCCURED DURING UPDATE",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        })
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        }
        if (requestCode == REQUEST_CODE2 && resultCode == Activity.RESULT_OK) {

            try {
                data?.let {
                    val inputStream: InputStream? =
                        context?.contentResolver?.openInputStream(it.data!!)
                    inputStream?.let { stream ->
                        showDialog()
                        mDialogView.progressBar.visibility = View.VISIBLE
                        viewModel.uploadVideo(stream)

                        viewModel.uploadVideo.observe(viewLifecycleOwner, Observer { result ->
                            Log.d("Result", result.toString())
                            when (result) {
                                Result.Loading -> {

                                }
                                is Result.Success -> {
                                    mDialogView.progressBar.visibility = View.INVISIBLE
                                    mDialogView.successImage.visibility = View.VISIBLE
                                }
                                is Result.Error -> {
                                    mAlertDialog?.dismiss()
                                    Toast.makeText(
                                        activity,
                                        "SORRY AN ERROR OCCURED DURING UPDATE",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        })

                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        if (requestCode == REQUEST_CODE3 && resultCode == Activity.RESULT_OK) {

            try {
                data?.let {
                    val inputStream: InputStream? =
                        context?.contentResolver?.openInputStream(it.data!!)
                    inputStream?.let { stream ->
                        showDialog()
                        mDialogView.progressBar.visibility = View.VISIBLE
                        viewModel.uploadPdf(stream)

                        viewModel.uploadPdf.observe(viewLifecycleOwner, Observer { result ->
                            Log.d("Result", result.toString())
                            when (result) {
                                Result.Loading -> {

                                }
                                is Result.Success -> {
                                    mDialogView.progressBar.visibility = View.INVISIBLE
                                    mDialogView.successImage.visibility = View.VISIBLE
                                }
                                is Result.Error -> {
                                    mAlertDialog?.dismiss()
                                    Toast.makeText(
                                        activity,
                                        "SORRY AN ERROR OCCURED DURING UPDATE",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        })
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

    }


    private fun selectImage() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun selectVideo() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_CODE2)
    }

    private fun selectPdf() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"

        }

        startActivityForResult(intent, REQUEST_CODE3)
    }

    private fun showDialog() {
        mDialogView = LayoutInflater.from(activity).inflate(R.layout.uploads, null)
        val mBuilder = activity?.let { it1 ->
            AlertDialog.Builder(it1)
                .setView(mDialogView)
        }
        mAlertDialog = mBuilder?.show()!!
    }
}