package com.mobileforce.hometeach.ui.studentpayments.makepayments

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.paystack.android.Paystack
import co.paystack.android.PaystackSdk
import co.paystack.android.Transaction
import co.paystack.android.exceptions.ExpiredAccessCodeException
import co.paystack.android.model.Card
import co.paystack.android.model.Charge
import com.google.android.material.snackbar.Snackbar
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentStudentMakePaymentBinding
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserCardDetailResponse
import com.mobileforce.hometeach.ui.studentpayments.carddetails.StudentCardsRecycler
import com.mobileforce.hometeach.utils.Result
import kotlinx.android.synthetic.main.fragment_student_make_payment.*
import kotlinx.android.synthetic.main.students_card_list.view.*
import kotlinx.coroutines.launch
import org.json.JSONException
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class StudentMakePaymentFragment : Fragment() {

    private lateinit var binding: FragmentStudentMakePaymentBinding
    private var cardList: ArrayList<UserCardDetailResponse> = ArrayList()
    private val viewModel: StudentMakePaymentViewModel by viewModel()
    private lateinit var onClickListener: View.OnClickListener
    private var transaction: Transaction? = null
    private lateinit var progressDialog: ProgressDialog
    private lateinit var charge: Charge
    private var cardNumber: String = ""
    private var cardCvc: String = ""
    private var cardExpMonth: Int = 0
    private var cardExpYear: Int = 0
    private var amount: Int = 0
    private var userEmail: String = ""
    private val backEndUrl: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentMakePaymentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserCardDetails()
        viewModel.user.observe(viewLifecycleOwner, androidx.lifecycle.Observer {user ->
            user?.let {
                userEmail = user.email
            }
        })

        onClickListener = View.OnClickListener {
            if (TextUtils.isEmpty(et_amount.text)){
                layout_amount.error = "Input an amount"
            } else {
                val holder = it.tag as RecyclerView.ViewHolder
                val pos = holder.adapterPosition
                val card = cardList[pos]
                cardNumber = card.card_number
                cardCvc = card.cvv
                cardExpMonth = card.expiry_month
                cardExpYear = card.expiry_year
                holder.itemView.rb_select_card.isChecked = true
            }
        }

        confirm_Button.setOnClickListener {
            when {
                TextUtils.isEmpty(et_amount.text) -> {
                    layout_amount.error = "Input an amount"
                }
                TextUtils.isEmpty(cardNumber) -> {
                    layout_amount.error = "Select a Card"
                }
                else -> {
                    amount = Integer.parseInt(et_amount.text.toString())
                    try {
                        startAFreshCharge(true)
                    } catch (e: Exception) {
                        val message = String.format("An error occurred while charging card: %s %s", e::class.java.simpleName, e.message)
                        displayErrorDialog(message)
                    }
                }
            }
        }

        viewModel.getUserCardDetails.observe(viewLifecycleOwner, androidx.lifecycle.Observer { result ->
            when (result) {
                Result.Loading -> {}
                is Result.Success -> {
                    for (card in result.data!!){
                        cardList.add(card)
                    }
                    binding.studentcardsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.studentcardsRecyclerView.hasFixedSize()
                    val adapter = StudentCardsRecycler()
                    adapter.submitList(result.data)
                    binding.studentcardsRecyclerView.adapter = adapter
                    adapter.setOnclickListener(onClickListener)
                }
            }
        })

//        val adapter2 = StudentPaymentsRecycler()
//        adapter2.submitList(student.payment)
//        binding.paymentsRecyclerView .adapter = adapter2
//        binding.paymentsRecyclerView.hasFixedSize()
}

    private fun loadCardFromForm(): Card? {
        val card = Card.Builder(cardNumber, 0, 0, "").build()
        card.cvc = cardCvc
        card.expiryMonth = cardExpMonth
        card.expiryYear = cardExpYear
        return card
    }

    private fun startAFreshCharge(local: Boolean){
        charge = Charge()
        charge.card = loadCardFromForm()

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Performing transaction... please wait")
        progressDialog.show()

        if (local) {
            charge.amount = amount
            charge.email = userEmail
            charge.reference = "ChargedFromAndroid_" + Calendar.getInstance().timeInMillis
            try {
                charge.putCustomField("Charged From", "Android SDK")
            } catch (e: JSONException){
                e.printStackTrace()
            }
            chargeCard()
        }
    }

    private fun chargeCard(){
        PaystackSdk.chargeCard(requireActivity(), charge, object: Paystack.TransactionCallback {
            override fun onSuccess(transaction: Transaction?) {
                dismissProgressDialog()
                this@StudentMakePaymentFragment.transaction = transaction
                Toast.makeText(activity, transaction?.reference, Toast.LENGTH_LONG).show()
                displayReferenceDialog()
                //verifyOnServer(transaction!!.reference)
            }

            override fun beforeValidate(transaction: Transaction?) {
                this@StudentMakePaymentFragment.transaction = transaction
                Toast.makeText(activity, transaction?.reference, Toast.LENGTH_LONG).show()
                displayReferenceDialog()
            }

            override fun onError(error: Throwable?, transaction: Transaction?) {
                this@StudentMakePaymentFragment.transaction = transaction
                if (error is ExpiredAccessCodeException){
                    startAFreshCharge(false)
                    chargeCard()
                    return
                }
                dismissProgressDialog()

                if (transaction?.reference != null){
                    Toast.makeText(activity, transaction.reference + " concluded with error: " + error?.message, Toast.LENGTH_LONG).show()
                    val message = String.format("%s  concluded with error: %s %s",
                        transaction.reference, error!!::class.java.simpleName, error.message)
                    displayErrorDialog(message)
                    //verifyOnServer(transaction.reference)
                } else{
                    Toast.makeText(activity, error?.message, Toast.LENGTH_LONG).show()
                    val message = String.format("Error: %s %s", error!!::class.java.simpleName, error.message)
                    displayErrorDialog(message)
                }
                displayReferenceDialog()
            }
        })
    }

    private fun dismissProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    private fun displayReferenceDialog() {
        if (transaction?.reference != null) {
            Snackbar.make(binding.materialCardView, String.format("Reference: %s", transaction?.reference), Snackbar.LENGTH_LONG)
        } else {
            Snackbar.make(binding.materialCardView, getString(R.string.no_transaction), Snackbar.LENGTH_LONG)
        }
    }

    private fun displayErrorDialog(message: String) {
        if (!TextUtils.isEmpty(message)) {
            Snackbar.make(binding.materialCardView, message, Snackbar.LENGTH_LONG)
        }
    }

    private fun verifyOnServer(reference: String) {
        var ref: String
        var error: String
        lifecycleScope.launch {
            try {
                ref = reference
                val url = URL("$backEndUrl/verify/$ref")
                val input = BufferedReader(InputStreamReader(url.openStream()))
                val inputLine: String
                inputLine = input.readLine()
                input.close()
            } catch (e: Exception) {
                error = e::class.java.simpleName + ": " + e.message
            }
        }
    }
}