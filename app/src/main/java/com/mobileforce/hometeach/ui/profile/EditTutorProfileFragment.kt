package com.mobileforce.hometeach.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.mobileforce.hometeach.data.sources.remote.Api
import androidx.fragment.app.Fragment
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.os.Build
import androidx.annotation.RequiresApi
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.data.model.UploadRequestBody
import com.mobileforce.hometeach.data.model.UploadResponse
import com.mobileforce.hometeach.utils.getFileName

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentEditTutorProfileBinding
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.credentials_list_view.*
import kotlinx.android.synthetic.main.fragment_edit_tutor_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlinx.android.synthetic.main.uploads.view.*
import org.koin.android.ext.android.get
import java.io.FileNotFoundException
import java.io.InputStream


/**
 * Authored by MayorJay
 */

@Suppress("UNREACHABLE_CODE")
class EditTutorProfileFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var binding: FragmentEditTutorProfileBinding
    val REQUEST_CODE = 1001
    val REQUEST_CODE2 = 1005
    val REQUEST_CODE3 = 1009
    lateinit var userImage: InputStream
    lateinit var userVideo: InputStream
    lateinit var userPdf: InputStream
    private lateinit var mDialogView:View

    private var selectedDocumentUri: Uri? = null

    companion object {
        fun newInstance() = EditTutorProfileFragment()
        private const val PICK_PDF_FILE = 2

    }
    private val viewModel: EditTutorViewModel = get<EditTutorViewModel>()

//    companion object {
//        fun newInstance() = EditTutorProfileFragment()
//    }

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
      
        return inflater.inflate(R.layout.fragment_edit_tutor_profile, container, false)
        tv_open_credentials.setOnClickListener {
            openFile()
        }

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

        // Displays the Credentials DialogFragment
        tv_view_all.setOnClickListener {
            val credentialDialog = CredentialDialog()
            val trans = parentFragmentManager.beginTransaction()
            credentialDialog.show(trans, "dialog")
        }

//        val profileList = viewModel.getProfileList()
//        val profileResponse = profileList[0]
//        var currentUserId: Int = 0
//        var currentUserEmail: String = ""
//        if (profileResponse.address == et_address_input.text.toString()){
//            currentUserId = profileResponse.id
//            currentUserEmail = profileResponse.email
//        }

//        binding.tvViewAll.setOnClickListener {
//
//
//        }


        bt_save_profile.setOnClickListener {
//            val profileData = Params.EditTutorProfile(
//                email = currentUserEmail,
//                full_name = et_name_input.text.toString(),
//                desc = et_description.text.toString(),
//                field = sp_select_field.selectedItem.toString(),
//                major_course = et_course_input.text.toString(),
//                other_courses = et_other_course_input.text.toString(),
//                state = sp_select_origin.selectedItem.toString(),
//                address = et_address_input.text.toString()
//            )
//            viewModel.editTutorProfile(currentUserId, profileData)
        }
     
    }
    fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/*"
            val mimeTypes = arrayOf("application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                "text/plain",
                "application/pdf",
                "application/zip")
            putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        startActivityForResult(intent,
            PICK_PDF_FILE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_PDF_FILE -> {
                    selectedDocumentUri = data?.data
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun loadDocument() {
        // Checks if the uri is empty
        if (selectedDocumentUri == null) {
            //  layout_root.snackbar("Select an Document First")
            return
        }

        val contentResolver = activity?.contentResolver;
        val cacheDir = activity?.externalCacheDir;
        val parcelFileDescriptor =
            contentResolver?.openFileDescriptor(selectedDocumentUri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(selectedDocumentUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        //progress_bar.progress = 0
        val body = UploadRequestBody(file, "document", this)
        Api.loadDocument(
            MultipartBody.Part.createFormData(
                "document",
                file.name,
                body
            ),
            RequestBody.create(MediaType.parse("multipart/form-data"), "json")
        ).enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                //layout_root.snackbar(t.message!!)
                //progress_bar.progress = 0
            }

            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                response.body()?.let {
                    //  layout_root.snackbar(it.message)
                    // progress_bar.progress = 100
                }
            }
        })
    }
  
        binding.selectImage.setOnClickListener {

        }
        binding.tvUpload.setOnClickListener {
            showDialog()

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
                        userImage = stream
                        mDialogView.imgCheck.visibility = View.VISIBLE
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
                        userVideo = stream
                        mDialogView.videoCheck.visibility = View.VISIBLE
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
                        userPdf = stream
                        mDialogView.pdfCheck.visibility = View.VISIBLE
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        }

//        upload(userId,userImage,userPdf,userImage)

    }

    fun upload(image: InputStream, credential: InputStream, video: InputStream) {

        viewModel.uploadTutorMedia(image, credential, video)

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
        mDialogView.image.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (activity?.let { it1 ->
                        ContextCompat.checkSelfPermission(
                            it1,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    } ==
                    PackageManager.PERMISSION_DENIED) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, REQUEST_CODE);
                } else {
                    //permission already granted
                    selectImage();
                }
            } else {
                //system OS is < Marshmallow
                selectImage();
            }


        }
        mDialogView.video.setOnClickListener {
            selectVideo()

        }
        mDialogView.pdf.setOnClickListener {
            selectPdf()
        }
        mDialogView.upload.setOnClickListener { mDialogView.progressBar.visibility = View.VISIBLE

            upload(userImage,userPdf,userImage)
            mDialogView.progressBar.visibility = View.INVISIBLE

        }


    }
}