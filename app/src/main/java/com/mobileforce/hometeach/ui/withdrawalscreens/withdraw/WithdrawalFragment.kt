package com.mobileforce.hometeach.ui.withdrawalscreens.withdraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.CircleTransform
import com.mobileforce.hometeach.databinding.FragmentWithdrawalBinding
import com.mobileforce.hometeach.ui.withdrawalscreens.Payment
import com.mobileforce.hometeach.ui.withdrawalscreens.TutorWithdrawalModel
import com.mobileforce.hometeach.utils.formatBalance
import com.mobileforce.hometeach.utils.loadImage
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Authored by enyason
 */
class WithdrawalFragment : Fragment() {

    private lateinit var payment_list: MutableList<Payment>
    private lateinit var binding: FragmentWithdrawalBinding

    private val viewModel: WithDrawalViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWithdrawalBinding.inflate(inflater, container, false)

        return binding.root //inflater.inflate(R.layout.fragment_withdrawal, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.profofile.observe(viewLifecycleOwner, Observer {

            binding.tutorImage.loadImage(it.profile_pic, R.drawable.profile_image, circular = true)
        })

        viewModel.user.observe(viewLifecycleOwner, Observer {

            it?.let {
                binding.tutorName.text = it.full_name
            }
        })

        viewModel.wallet.observe(viewLifecycleOwner, Observer {

            it?.let {
                binding.tutorBalance.text = it.availableBalance.formatBalance()
            }
        })

        payment_list = mutableListOf()
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
        var Tutor =
            TutorWithdrawalModel(
                1,
                payment_list,
                "Rahman Django",
                "profile_image",
                "215000 N"
            )
        binding.tutorName.text = Tutor.tutorName
        binding.tutorBalance.text = "Balance: " + Tutor.balance
        Picasso.get().load("profile_image").transform(CircleTransform())
            .placeholder(R.drawable.profile_image).error(R.drawable.profile_image)
            .into(binding.tutorImage)


        val adapter =
            PaymentRecycler()
        adapter.submitList(Tutor.payment)
        binding.withdrawalRecyclerView.adapter = adapter
        binding.withdrawalRecyclerView.hasFixedSize()
    }

}

