package com.mobileforce.hometeach.ui.studentpayments.carddetails

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
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.databinding.FragmentStudentAddCardDetailsBinding
import kotlinx.android.synthetic.main.fragment_my_banks.*
import org.koin.android.viewmodel.ext.android.viewModel


class StudentAddCardDetailsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentStudentAddCardDetailsBinding
    private val viewModel: StudentAddCardDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentStudentAddCardDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val toolbar = binding.toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow)
        }
        //val userEntity = viewModel.getUserDetailsFromDb()
        val cardNumber = binding.etCardNumber.text
        val cardCvc = binding.etCvcNumber.text
        val expiryMonth = binding.etMonth.text
        val expiryYear = binding.etYear.text
        val btnSave = binding.save
        var userId: String = ""
        var userName: String = ""
        viewModel.user.observe(viewLifecycleOwner, androidx.lifecycle.Observer {user ->
            user?.let {
                userId = user.id
                userName = user.full_name
            }
        })

        btnSave.setOnClickListener {

            if (cardNumber.isNullOrEmpty()){
                binding.etCardNumber.error = "Input Card Number"
            }

            if (cardCvc.isNullOrEmpty()){
                binding.etCvcNumber.error = "Input Card CVV"
            }

            if (expiryMonth.isNullOrEmpty()){
                binding.etMonth.error = "Input Expiry Month"
            }

            if (expiryYear.isNullOrEmpty()){
                binding.etYear.error = "Input Expiry Year"
            }
            showDialog()
            // send card details to endpoint
            viewModel.saveUserCardDetails(
                Params.CardDetails(
                userId,
                userName,
                cardNumber.toString().trim(),
                cardCvc.toString().trim(),
                expiryMonth.toString().trim().toInt(),
                expiryYear.toString().trim().toInt())
            )
        }

        toolbar.setNavigationOnClickListener {
            navController.navigate(R.id.studentCardDetails)
        }
    }

    private fun showDialog(){
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.add_card_dialog, null)
        val mBuilder = activity?.let { it1 ->
            AlertDialog.Builder(it1)
                .setView(mDialogView)
        }
        mBuilder?.show()
    }
}