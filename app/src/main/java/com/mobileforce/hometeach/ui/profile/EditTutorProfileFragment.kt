package com.mobileforce.hometeach.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.data.model.ProfileEntity
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.databinding.FragmentEditTutorProfileBinding
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.fragment_edit_tutor_profile.*
import kotlinx.android.synthetic.main.uploads.view.*
import org.koin.android.ext.android.get
import java.io.FileNotFoundException
import java.io.InputStream



/**
 * Authored by MayorJay
 */

class EditTutorProfileFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var binding: FragmentEditTutorProfileBinding
    val REQUEST_CODE = 1001
    val REQUEST_CODE2 = 1005
    val REQUEST_CODE3 = 1009
    lateinit var userImage: InputStream
    private lateinit var mDialogView: View

    private val viewModel: EditTutorViewModel = get<EditTutorViewModel>()
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

    //private val viewModel: EditTutorProfileViewModel by viewModel()

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
            val selectedField = binding.spSelectField.selectedItem.toString()
            val majorCourses = binding.etCourseInput.text.toString()
            val otherCourses = binding.etOtherCourseInput.text.toString()
            val stateOfOrigin = binding.spSelectOrigin.selectedItem.toString()
            val address = binding.etAddressInput.text.toString()
            val rate = binding.etRateInput.text.toString()
            val description = binding.etDescription.text.toString()
            showDialog()
            mDialogView.progressBar.visibility = View.VISIBLE
            val data = Params.UpdateTutorProfile(selectedField,majorCourses,otherCourses,stateOfOrigin,address,rate,description)
            viewModel.updateTutorProfile(data)
            mDialogView.progressBar.visibility = View.INVISIBLE
            mDialogView.successImage.visibility = View.VISIBLE

        }
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
                        mDialogView.progressBar.visibility = View.INVISIBLE
                        mDialogView.successImage.visibility = View.VISIBLE

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
                        mDialogView.progressBar.visibility = View.INVISIBLE
                        mDialogView.successImage.visibility = View.VISIBLE
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
                          mDialogView.progressBar.visibility = View.INVISIBLE
                        mDialogView.successImage.visibility = View.VISIBLE
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
        val mAlertDialog = mBuilder?.show()
    }

}