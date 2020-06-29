package com.mobileforce.hometeach.ui.withdrawal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.*
import com.mobileforce.hometeach.bindTutorImage
import com.mobileforce.hometeach.databinding.ActivityLoginBinding
import com.mobileforce.hometeach.databinding.FragmentWithdrawalBinding
import com.mobileforce.hometeach.databinding.ListItemClassBinding
import com.mobileforce.hometeach.databinding.WithdrawalListItemBinding
import com.mobileforce.hometeach.loadImage
import com.mobileforce.hometeach.models.*
import com.mobileforce.hometeach.ui.message.ChatListViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.chat_list_message_item.view.*
import kotlinx.android.synthetic.main.fragment_chat_list.*
import kotlinx.android.synthetic.main.fragment_ongoing.*

/**
 * Authored by enyason
 */
class WithdrawalFragment : Fragment() {

    private lateinit var payment_list:MutableList<Payment>
    private lateinit var binding: FragmentWithdrawalBinding

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
        payment_list = mutableListOf()
        payment_list.add(Payment(1,"20 June 2020","50000 N","Success"))
        payment_list.add(Payment(1,"20 June 2020","50000 N","Success"))
        payment_list.add(Payment(1,"20 June 2020","50000 N","Failed"))
        payment_list.add(Payment(1,"20 June 2020","50000 N","Success"))
        var Tutor =  TutorWithdrawalModel(1,payment_list,"Rahman Django","profile_image","215000 N")
        binding.tutorName.text = Tutor.tutorName
        binding.tutorBalance.text = "Balance: "+Tutor.balance
        Picasso.get().load("profile_image").transform(CircleTransform()).placeholder(R.drawable.profile_image).error(R.drawable.profile_image).into(binding.tutorImage)


        val adapter =  PaymentRecycler()
        adapter.submitList(Tutor.payment)
        binding.withdrawalRecyclerView.adapter = adapter
        binding.withdrawalRecyclerView.hasFixedSize()
    }

}

