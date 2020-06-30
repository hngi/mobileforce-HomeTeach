package com.mobileforce.hometeach.TutorDashBoardFragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mobileforce.hometeach.R


class AddCardDetails : Fragment() {
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_card_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow)
        }
        val acc_name = view.findViewById<EditText>(R.id.acc_name)
        val txt_accname = acc_name.text
        val acc_number = view.findViewById<EditText>(R.id.acc_number)
        val txt_accnumber = acc_number.text
        val card_expiry_date = view.findViewById<EditText>(R.id.m_y)
        val txt_card_expiry_date = card_expiry_date.text
        val cw_number = view.findViewById<EditText>(R.id.cw)
        val txt_cw_number = cw_number.text
        val btn_save = view.findViewById<Button>(R.id.save)

        btn_save.setOnClickListener {

            if (txt_accname.isNullOrEmpty())
            {

            }

            if (txt_accnumber.isNullOrEmpty())
            {

            }

            if (txt_card_expiry_date.isNullOrEmpty())
            {

            }

            if (txt_cw_number.isNullOrEmpty())
            {

            }

           showDialog()


        }

        toolbar.setNavigationOnClickListener {
            navController.navigate(R.id.cardDetails)
        }

    }

   private fun showDialog()
    {
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.add_card_dialog, null)
        val mBuilder = activity?.let { it1 ->
            AlertDialog.Builder(it1)
                .setView(mDialogView)

        }
        //show dialog
        val  mAlertDialog = mBuilder?.show()


    }

}