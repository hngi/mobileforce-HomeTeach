package com.mobileforce.hometeach.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentAddBankBinding
import com.mobileforce.hometeach.databinding.FragmentStudentProfileBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * A simple [Fragment] subclass.
 * Use the [StudentProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentProfileFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var binding:FragmentStudentProfileBinding
    val  REQUEST_CODE = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentStudentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.ProfileImage.setOnClickListener {
            Log.d("api","IMAGE CLICKED")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                if (activity?.let { it1 -> checkSelfPermission(it1,Manifest.permission.READ_EXTERNAL_STORAGE) } ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, REQUEST_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
        }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            binding.ProfileImage.setImageURI(data!!.data)
            var file = File(data!!.data!!.path)
            var requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
            var filePart = MultipartBody.Part.createFormData("upload_file", file.name, requestBody)
//            viewModel.uploadImage(filePart).observe(this, Observer {
//                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
//            })

        }
    }
    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

}