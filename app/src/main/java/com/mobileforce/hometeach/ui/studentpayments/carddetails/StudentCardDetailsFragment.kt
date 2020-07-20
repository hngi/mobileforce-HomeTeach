package com.mobileforce.hometeach.ui.studentpayments.carddetails

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.CircleTransform
import com.mobileforce.hometeach.databinding.FragmentStudentCardDetailsBinding
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import com.mobileforce.hometeach.ui.studentpayments.StudentCardModel
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel


class StudentCardDetailsFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var binding: FragmentStudentCardDetailsBinding
    //private lateinit var list: MutableList<com.mobileforce.hometeach.ui.studentpayments.UserCardDetailResponse>
    private var cardList: List<UserCardDetailResponse> = ArrayList()
    private val viewModel: StudentCardDetailsViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentStudentCardDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get a list of User's saved cards to display in the RV
        cardList = viewModel.getUserCardDetails()
//        list = mutableListOf()
//        list.add(
//            com.mobileforce.hometeach.ui.studentpayments.UserCardDetailResponse(
//                1,
//                R.drawable.ic_visa,
//                "....2019",
//                true
//            )
//        )
//        list.add(
//            com.mobileforce.hometeach.ui.studentpayments.UserCardDetailResponse(
//                2,
//                R.drawable.ic_master,
//                "....3200",
//                false
//            )
//        )
//
//        var cards = StudentCardModel(
//            1,
//            list,
//            "Rahman Django",
//            "profile_image",
//            "215000 N"
//        )
//        binding.username.text = cards.tutorName
//        binding.balance.text = "Balance: "+cards.balance
//        Picasso.get().load("profile_image").transform(CircleTransform()).placeholder(R.drawable.profile_image).error(R.drawable.profile_image).into(binding.userImage)


        binding.rvCreditCards.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCreditCards.hasFixedSize()
        val adapter = StudentCardsRecycler()
        adapter.submitList(cardList)
        binding.rvCreditCards.adapter = adapter

        navController = Navigation.findNavController(view)
        val toolbar = binding.toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow)
        }
        val userName = binding.username
        val userImage = binding.userImage
        val btnCancel = binding.btnCancel
        val addCard = binding.addCard
        val balance = binding.balance

        viewModel.user.observe(viewLifecycleOwner, androidx.lifecycle.Observer {user ->
            user?.let {
                userName.text = user.full_name
            }
        })

        toolbar.setNavigationOnClickListener {
            navController.navigate(R.id.tutorHomePageFragment)
        }

        addCard.setOnClickListener {
            navController.navigate(R.id.studentAddCardDetails)
        }
    }
}