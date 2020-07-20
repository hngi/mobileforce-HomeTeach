package com.mobileforce.hometeach.ui.studentpayments.makepayments

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
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
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentStudentMakePaymentBinding
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import com.mobileforce.hometeach.ui.studentpayments.carddetails.StudentCardsRecycler
import kotlinx.android.synthetic.main.fragment_student_make_payment.*
import kotlinx.android.synthetic.main.payment_reference_dialog.*
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

//    private lateinit var card_DetailResponse_list:MutableList<UserCardDetailResponse>
//    private lateinit var payment_list:MutableList<Payment>
    private lateinit var binding: FragmentStudentMakePaymentBinding
    private var cardList: List<UserCardDetailResponse> = ArrayList()
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
        // return inflater.inflate(R.layout.fragment_card_detail, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get a list of User's saved cards to display in the RV
        cardList = viewModel.getUserCardDetails()
        viewModel.user.observe(viewLifecycleOwner, androidx.lifecycle.Observer {user ->
            user?.let {
                userEmail = user.email
            }
        })

        onClickListener = View.OnClickListener {
            val holder = it.tag as RecyclerView.ViewHolder
            val pos = holder.adapterPosition
            val card = cardList[pos]
            cardNumber = card.cardNumber
            cardCvc = card.cardCvc
            cardExpMonth = card.expiryMonth
            cardExpYear = card.expiryYear
            if (TextUtils.isEmpty(amount.toString())){
                layout_amount.error = "Input an amount"
            } else {
                amount = et_amount.toString().trim().toInt()
            }
            holder.itemView.rb_select_card.isChecked = true
        }

        confirm_Button.setOnClickListener {
            try {
                startAFreshCharge(true)
            } catch (e: Exception) {
                val message = String.format("An error occurred while charging card: %s %s", e::class.java.simpleName, e.message)
                displayErrorDialog(message)
            }
        }
//        card_DetailResponse_list = mutableListOf()
//        payment_list = mutableListOf()
//        card_DetailResponse_list.add(
//            UserCardDetailResponse(
//                1,
//                R.drawable.ic_visa,
//                "....2019",
//                true
//            )
//        )
//        card_DetailResponse_list.add(
//            UserCardDetailResponse(
//                2,
//                R.drawable.ic_master,
//                "....3200",
//                false
//            )
//        )
//
//        payment_list.add(
//            Payment(
//                1,
//                "20 June 2020",
//                "50000 N",
//                "Success"
//            )
//        )
//        payment_list.add(
//            Payment(
//                1,
//                "20 June 2020",
//                "50000 N",
//                "Success"
//            )
//        )
//        payment_list.add(
//            Payment(
//                1,
//                "20 June 2020",
//                "50000 N",
//                "Failed"
//            )
//        )
//        payment_list.add(
//            Payment(
//                1,
//                "20 June 2020",
//                "50000 N",
//                "Success"
//            )
//        )
//        val student =
//            StudentPaymentModel(payment_list, card_DetailResponse_list,
//                "Rahman Django",
//                "profile_image",
//                "215000 N"
//            )
//        binding.tutorName.text = student.tutorName
//        binding.tutorBalance.text = "Balance: "+ student.balance
//        Picasso.get().load("profile_image").transform(CircleTransform()).placeholder(R.drawable.profile_image).error(R.drawable.profile_image).into(binding.tutorImage)


        binding.studentcardsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.studentcardsRecyclerView.hasFixedSize()
        val adapter = StudentCardsRecycler()
        adapter.submitList(cardList)
        binding.studentcardsRecyclerView .adapter = adapter
        adapter.setOnclickListener(onClickListener)

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
        return if (card.isValid) {
            card
        } else{
            null
        }
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
        val dialogView = PaymentReferenceDialog.newInstance()
        if (transaction?.reference != null){
            dialogView.tv_reference_text.text = String.format("Reference: %s", transaction?.reference)
            dialogView.show(requireActivity().supportFragmentManager, "reference_dialog")
        } else {
            dialogView.tv_reference_text.text = getString(R.string.no_transaction)
            dialogView.show(requireActivity().supportFragmentManager, "reference_dialog")
        }
    }

    private fun displayErrorDialog(message: String) {
        val dialogView = PaymentReferenceDialog.newInstance()
        if (!TextUtils.isEmpty(message)){
            dialogView.tv_reference_text.text = message
        }
        dialogView.show(requireActivity().supportFragmentManager, "error_dialog")
    }

    override fun onPause() {
        super.onPause()

        dismissProgressDialog()
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

class PaymentReferenceDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.payment_reference_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_close.setOnClickListener {
            dismiss()
        }
    }

    companion object{
        fun newInstance(): PaymentReferenceDialog {
            return PaymentReferenceDialog()
        }
    }
}