package com.mobileforce.hometeach.ui.studentpayments.makepayments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.CircleTransform
import com.mobileforce.hometeach.databinding.FragmentStudentMakePaymentBinding
import com.mobileforce.hometeach.ui.studentpayments.StudentCard
import com.mobileforce.hometeach.ui.studentpayments.Payment
import com.mobileforce.hometeach.ui.studentpayments.StudentPaymentModel
import com.mobileforce.hometeach.ui.studentpayments.carddetails.StudentPaymentsRecycler
import com.squareup.picasso.Picasso

//Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudentMakePaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentMakePaymentFragment : Fragment() {
    //Rename and change types of parameters
    private lateinit var card_list:MutableList<StudentCard>
    private lateinit var payment_list:MutableList<Payment>
    private lateinit var binding: FragmentStudentMakePaymentBinding

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
        card_list = mutableListOf()
        payment_list = mutableListOf()
        card_list.add(
            StudentCard(
                1,
                R.drawable.ic_visa,
                "....2019",
                true
            )
        )
        card_list.add(
            StudentCard(
                2,
                R.drawable.ic_master,
                "....3200",
                false
            )
        )

        payment_list.add(
            Payment(
                1,
                "20 June 2020",
                "50000 N",
                "Success"
            )
        )
        payment_list.add(
            Payment(
                1,
                "20 June 2020",
                "50000 N",
                "Success"
            )
        )
        payment_list.add(
            Payment(
                1,
                "20 June 2020",
                "50000 N",
                "Failed"
            )
        )
        payment_list.add(
            Payment(
                1,
                "20 June 2020",
                "50000 N",
                "Success"
            )
        )
        var Student =
            StudentPaymentModel(payment_list, card_list,
                "Rahman Django",
                "profile_image",
                "215000 N"
            )
        binding.tutorName.text = Student.tutorName
        binding.tutorBalance.text = "Balance: "+ Student.balance
        Picasso.get().load("profile_image").transform(CircleTransform()).placeholder(R.drawable.profile_image).error(R.drawable.profile_image).into(binding.tutorImage)


        val adapter =
            StudentCardsRecycler()
        adapter.submitList(Student.cards)
        binding.studentcardsRecyclerView .adapter = adapter
        binding.studentcardsRecyclerView.hasFixedSize()

        val adapter2 =
            StudentPaymentsRecycler()
        adapter2.submitList(Student.payment)
        binding.paymentsRecyclerView .adapter = adapter2
        binding.paymentsRecyclerView.hasFixedSize()
    }

}