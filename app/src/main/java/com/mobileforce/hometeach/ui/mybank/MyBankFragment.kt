package com.mobileforce.hometeach.ui.mybank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.CircleTransform
import com.mobileforce.hometeach.adapters.MyBankRecycler
import com.mobileforce.hometeach.adapters.PaymentRecycler
import com.mobileforce.hometeach.databinding.FragmentMyBankBinding
import com.mobileforce.hometeach.databinding.FragmentWithdrawalBinding
import com.mobileforce.hometeach.models.MyBank
import com.mobileforce.hometeach.models.Payment
import com.mobileforce.hometeach.models.MyBankModel
import com.squareup.picasso.Picasso



/**
 * A simple [Fragment] subclass.
 * Use the [MyBankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyBankFragment : Fragment() {

    private lateinit var bank_list:MutableList<MyBank>
    private lateinit var binding: FragmentMyBankBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyBankBinding.inflate(inflater, container, false)

        return binding.root
        //return inflater.inflate(R.layout.fragment_my_bank, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bank_list = mutableListOf()
        bank_list.add(MyBank(1,"Diamond Bank","08667821",true))
        bank_list.add(MyBank(1,"First Bank","1098833",false))

        var banks =  MyBankModel(1,bank_list,"Rahman Django","profile_image","215000 N")
        binding.tutorName.text = banks.tutorName
        binding.tutorBalance.text = "Balance: "+banks.balance
        Picasso.get().load("profile_image").transform(CircleTransform()).placeholder(R.drawable.profile_image).error(R.drawable.profile_image).into(binding.tutorImage)


        val adapter =  MyBankRecycler()
        adapter.submitList(banks.banks)
        binding.mybankRecyclerView.adapter = adapter
        binding.mybankRecyclerView.hasFixedSize()
    }


}