package com.mobileforce.hometeach.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.databinding.FragmentEditStudentProfileBinding
import com.mobileforce.hometeach.utils.ApiError
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.loadImage
import com.mobileforce.hometeach.utils.toast
import kotlinx.android.synthetic.main.uploads.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.FileNotFoundException
import java.io.InputStream
import java.net.URL

/**
 * Created by MayorJay
 */
class EditStudentProfileFragment : Fragment() {

    private val viewModel: EditStudentProfileViewModel by viewModel()
    private lateinit var binding: FragmentEditStudentProfileBinding
    private val Request_Code = 1000
    private lateinit var mDialogView: View
    private lateinit var mAlertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditStudentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            binding.studentName.text = user.full_name
            binding.etPhoneNumber.setText(user.phone_number)
        })
        viewModel.profile.observe(viewLifecycleOwner, Observer { profile ->
            binding.etAbout.setText(profile.desc)
            binding.profilePic.loadImage(URL(profile.profile_pic)) //.setImageResource(profile.profile_pic!!.toInt())
        })

        viewModel.updateStudentProfile.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                Result.Loading -> {}
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

        viewModel.uploadStudentPhoto.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                Result.Loading -> {}
                is Result.Success -> {
                    mDialogView.progressBar.visibility = View.INVISIBLE
                    mDialogView.successImage.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    mAlertDialog.dismiss()
                    toast(message = "Oops! An error occurred")
                }
            }
        })

        binding.profilePic.setOnClickListener {
            selectImage()
        }

        binding.btSaveProfile.setOnClickListener {
            val desc = binding.etAbout.text.toString()
            val phoneNumber = binding.etPhoneNumber.text.toString()
            val data = Params.UpdateStudentProfile(desc, phoneNumber)
            viewModel.updateStudentProfile(data)
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, Request_Code)
    }

    private fun showDialog() {
        mDialogView = LayoutInflater.from(activity).inflate(R.layout.uploads, null)
        val mBuilder = activity?.let { it1 ->
            AlertDialog.Builder(it1)
                .setView(mDialogView)
        }
        mAlertDialog = mBuilder?.show()!!
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Request_Code && resultCode == Activity.RESULT_OK) {
            try {
                data?.let {
                    val inputStream: InputStream? =
                        context?.contentResolver?.openInputStream(it.data!!)
                    inputStream?.let { stream ->
                        showDialog()
                        mDialogView.progressBar.visibility = View.VISIBLE
                        viewModel.uploadStudentPhoto(stream)
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}