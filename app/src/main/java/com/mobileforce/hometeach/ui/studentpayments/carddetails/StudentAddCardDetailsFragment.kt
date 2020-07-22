package com.mobileforce.hometeach.ui.studentpayments.carddetails

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.databinding.FragmentStudentAddCardDetailsBinding
import kotlinx.android.synthetic.main.add_card_dialog.*
import kotlinx.android.synthetic.main.fragment_student_add_card_details.*
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
        val cardNumber = binding.etCardNumber.text.toString()
        val cardCvc = binding.etCvcNumber.text.toString()
        val expiryMonth = binding.etMonth.text
        val expiryYear = binding.etYear.text
        val btnSave = binding.save
        var userId = ""
        var userName = ""
        viewModel.user.observe(viewLifecycleOwner, androidx.lifecycle.Observer {user ->
            user?.let {
                userId = user.id
                userName = user.full_name
                username.text = user.full_name
            }
        })

        btnSave.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.etCardNumber.text) -> {
                    binding.etCardNumber.error = "Input Card Number"
                }
                TextUtils.isEmpty(binding.etCvcNumber.text) -> {
                    binding.etCvcNumber.error = "Input Card CVV"
                }
                TextUtils.isEmpty(binding.etMonth.text) -> {
                    binding.etMonth.error = "Input Expiry Month"
                }
                TextUtils.isEmpty(binding.etYear.text) -> {
                    binding.etYear.error = "Input Expiry Year"
                }
                else -> {
                    // send card details to endpoint
                    val cardDetails = Params.CardDetails(
                        user = userId,
                        card_holder_name = userName,
                        card_number = cardNumber,
                        cvv = cardCvc,
                        expiry_month = Integer.parseInt(expiryMonth.toString()),
                        expiry_year = Integer.parseInt(expiryYear.toString())
                    )
                    viewModel.saveUserCardDetails(cardDetails)
                    binding.etCardNumber.text.clear()
                    binding.etCvcNumber.text.clear()
                    binding.etMonth.text.clear()
                    binding.etYear.text.clear()
                    showDialog()
                }
            }
        }

        toolbar.setNavigationOnClickListener {
            navController.navigate(R.id.studentCardDetails)
        }
    }

    private fun showDialog() {
        val mDialogView = AddCardDialog.newInstance()
        mDialogView.show(requireActivity().supportFragmentManager, "add_card")
    }
}

class AddCardDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_card_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_close.setOnClickListener {
            dismiss()
        }
    }

    companion object{
        fun newInstance(): AddCardDialog {
            return AddCardDialog()
        }
    }
}