package com.mobileforce.hometeach.ui.withdrawalscreens.carddetails
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
import com.mobileforce.hometeach.databinding.FragmentTutorAddCardDetailsBinding


class TutorAddCardDetailsFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var binding:FragmentTutorAddCardDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentTutorAddCardDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val toolbar = binding.toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow)
        }
        val cardNumber = binding.etCardNumber.text
        val cvvNumber = binding.etCvcNumber.text
        val expiryMonth = binding.etMonth.text
        val expiryYear = binding.etYear.text
        val btnSave = binding.save

        btnSave.setOnClickListener {

            if (cardNumber.isNullOrEmpty())
            {

            }

            if (cvvNumber.isNullOrEmpty())
            {

            }

            if (expiryMonth.isNullOrEmpty())
            {

            }

            if (expiryYear.isNullOrEmpty())
            {

            }

           showDialog()


        }

        toolbar.setNavigationOnClickListener {
            navController.navigate(R.id.tutorCardDetails)
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