package com.mobileforce.hometeach.ui.tutordetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentTutorDetailsBinding
import com.mobileforce.hometeach.ui.tutorlist.DialogData
import com.mobileforce.hometeach.ui.tutorlist.SelectDateDialog
import kotlinx.android.synthetic.main.request_layout.view.*


class TutorDetailsFragment : Fragment(), SelectDateDialog.SelectDateListener{
    private lateinit var binding:FragmentTutorDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.requestService.setOnClickListener {
            val selectDateDialog = SelectDateDialog()
            selectDateDialog.showPopupWindow(requireView(),this)
        }

        binding.viewAll.setOnClickListener {
            val mDialogView = LayoutInflater.from(activity).inflate(R.layout.credentials_dialog, null)
            val mBuilder = activity?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)

            }

            val  mAlertDialog = mBuilder?.show()
          mBuilder?.setTitle("Credentials")
            mDialogView.btn.setOnClickListener {
                mAlertDialog?.hide()
            }
        }


    }

    override fun onApproveClicked(dialogData: DialogData) {

    }


}