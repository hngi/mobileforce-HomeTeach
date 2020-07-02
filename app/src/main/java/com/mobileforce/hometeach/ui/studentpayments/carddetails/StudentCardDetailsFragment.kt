package com.mobileforce.hometeach.ui.studentpayments.carddetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.CircleTransform
import com.mobileforce.hometeach.databinding.FragmentStudentCardDetailsBinding
import com.mobileforce.hometeach.ui.studentpayments.Payment
import com.mobileforce.hometeach.ui.studentpayments.StudentCard
import com.mobileforce.hometeach.ui.studentpayments.StudentCardModel
import com.mobileforce.hometeach.ui.studentpayments.StudentPaymentModel
import com.mobileforce.hometeach.ui.studentpayments.carddetails.StudentPaymentsRecycler
import com.mobileforce.hometeach.ui.studentpayments.makepayments.StudentCardsRecycler
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudentCardDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentCardDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var card_list:MutableList<StudentCard>
    private lateinit var binding: FragmentStudentCardDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentCardDetailsBinding.inflate(inflater, container, false)

        return binding.root //inflater.inflate(R.layout.fragment_withdrawal, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        card_list = mutableListOf()
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

        var cards = StudentCardModel(
            1,
            card_list,
            "Rahman Django",
            "profile_image",
            "215000 N"
        )
        binding.tutorName.text = cards.tutorName
        binding.tutorBalance.text = "Balance: "+cards.balance
        Picasso.get().load("profile_image").transform(CircleTransform()).placeholder(R.drawable.profile_image).error(R.drawable.profile_image).into(binding.tutorImage)


        val adapter =
            StudentCardsRecycler()
        adapter.submitList(cards.cards)
        binding.studentcardsRecyclerView .adapter = adapter
        binding.studentcardsRecyclerView.hasFixedSize()

    }

}

