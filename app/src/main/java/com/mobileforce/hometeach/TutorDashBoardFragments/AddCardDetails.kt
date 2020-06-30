package com.mobileforce.hometeach.TutorDashBoardFragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentAddCardDetailsBinding


class AddCardDetails : Fragment() {
    lateinit var navController: NavController

    lateinit var binding:FragmentAddCardDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentAddCardDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val toolbar = binding.toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow)
        }
        val accName = binding.accName
        val txtAccname = accName.text
        val accNumber = binding.accNumber
        val txtAccnumber = accNumber.text
        val cardExpiryDate = binding.mY
        val txtCardExpiryDate = cardExpiryDate.text
        val cwNumber = binding.cw
        val txtCwNumber = cwNumber.text
        val btnSave =binding.save

        btnSave.setOnClickListener {

            if (txtAccname.isNullOrEmpty())
            {

            }

            if (txtAccnumber.isNullOrEmpty())
            {

            }

            if (txtCardExpiryDate.isNullOrEmpty())
            {

            }

            if (txtCwNumber.isNullOrEmpty())
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