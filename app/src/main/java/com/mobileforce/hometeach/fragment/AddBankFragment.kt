package com.mobileforce.hometeach.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mobileforce.hometeach.R


class AddBankFragment : Fragment() {
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow)
        }
        val acc_name = view.findViewById<EditText>(R.id.acc_name)
        val txt_acc_name = acc_name.text
        val acc_number = view.findViewById<EditText>(R.id.acc_number)
        val txt_acc_number = acc_number.text
        val acc_route = view.findViewById<EditText>(R.id.acc_route)
        val txt_acc_route = acc_route.text
        val ss_number = view.findViewById<EditText>(R.id.acc_security_number)
        val txt_ss_number = ss_number.text
        val btn_save = view.findViewById<Button>(R.id.save)

        toolbar.setNavigationOnClickListener {

            navController.navigate(R.id.myBanks)
        }

        btn_save.setOnClickListener {

            if (txt_acc_name.isNullOrEmpty())
            {

            }
            if (txt_acc_number.isNullOrEmpty())
            {

            }
            if (txt_acc_route.isNullOrEmpty())
            {

            }
            if (txt_ss_number.isNullOrEmpty())
            {

            }

            showDialog()
        }

    }

    private fun showDialog() {
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.save_bank_dialog, null)
        val mBuilder = activity?.let { it1 ->
            AlertDialog.Builder(it1)
                .setView(mDialogView)
        }
        //show dialog
        val mAlertDialog = mBuilder?.show()


    }
}