package com.mobileforce.hometeach.ui.studentpayments.carddetails

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentStudentCardDetailsBinding
<<<<<<< HEAD
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserCardDetailResponse
import com.mobileforce.hometeach.utils.Result
=======
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.formatBalance
import com.mobileforce.hometeach.utils.loadImage
>>>>>>> 571221e391ceedd0b127e32b52e5d73a9e805efd
import org.koin.android.viewmodel.ext.android.viewModel


class StudentCardDetailsFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var binding: FragmentStudentCardDetailsBinding
    private lateinit var cardList: List<UserCardDetailResponse>
    private lateinit var onClickListener: View.OnClickListener
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
        navController = Navigation.findNavController(view)
        val toolbar = binding.toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow)
        }

        viewModel.getUserCardDetails()
        onClickListener = View.OnClickListener {}

        viewModel.getUserCardDetails.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                Result.Loading -> {}
                is Result.Success -> {
                    binding.rvCreditCards.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvCreditCards.hasFixedSize()
                    val adapter = StudentCardsRecycler()
                    adapter.submitList(result.data)
                    binding.rvCreditCards.adapter = adapter
                    adapter.setOnclickListener(onClickListener)
                }
            }
        })

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


        viewModel.profile.observe(viewLifecycleOwner, Observer {

            binding.userImage.loadImage(it.profile_pic, circular = true)
        })

        viewModel.wallet.observe(viewLifecycleOwner, Observer {

            it?.let {
                binding.balance.text = it.availableBalance.formatBalance()
            }
        })
    }
}