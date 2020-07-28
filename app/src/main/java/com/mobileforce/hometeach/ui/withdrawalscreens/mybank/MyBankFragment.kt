package com.mobileforce.hometeach.ui.withdrawalscreens.mybank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mobileforce.hometeach.databinding.FragmentMyBankBinding
import com.mobileforce.hometeach.ui.withdrawalscreens.MyBank
import com.mobileforce.hometeach.ui.withdrawalscreens.MyBankModel
import com.mobileforce.hometeach.utils.formatBalance
import com.mobileforce.hometeach.utils.loadImage
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 * Use the [MyBankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyBankFragment : Fragment() {

    private lateinit var bank_list: MutableList<MyBank>
    private lateinit var binding: FragmentMyBankBinding

    private val viewModel: MyBankViewModel by viewModel()


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

        binding.toolbar.setNavigationOnClickListener {

            findNavController().popBackStack()
        }
        bank_list = mutableListOf()
        bank_list.add(
            MyBank(
                1,
                "Diamond Bank",
                "08667821",
                true
            )
        )
        bank_list.add(
            MyBank(
                1,
                "First Bank",
                "1098833",
                false
            )
        )

        var banks = MyBankModel(
            1,
            bank_list,
            "Rahman Django",
            "profile_image",
            "215000 N"
        )

        val adapter =
            MyBankRecycler()
        adapter.submitList(banks.banks)
        binding.mybankRecyclerView.adapter = adapter
        binding.mybankRecyclerView.hasFixedSize()


        viewModel.profofile.observe(viewLifecycleOwner, Observer {

            binding.tutorImage.loadImage(it.profile_pic, circular = true)
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
    }


}