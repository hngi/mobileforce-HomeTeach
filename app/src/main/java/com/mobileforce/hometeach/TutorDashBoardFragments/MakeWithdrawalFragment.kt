package com.mobileforce.hometeach.TutorDashBoardFragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentMakeWithdrawalBinding


class MakeWithdrawalFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var binding:FragmentMakeWithdrawalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentMakeWithdrawalBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val toolbar = binding.toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow)
        }
        val amount = binding.amount
        val txtAmount = amount.text
        val btnCancel =binding.btnCancel
        val btnConfirm = binding.btnConfirm
        val withdrawalHistory = binding.withdrawalHistoryRecycler
        val username =binding.username
        val balance = binding.balance
        val userImage = binding.userImage

        toolbar.setNavigationOnClickListener {

            navController.navigate(R.id.tutorHomePageFragment)
        }

        btnCancel.setOnClickListener {
            amount.setText("")
        }

        btnConfirm.setOnClickListener {
            showDialog()
        }

    }

    private fun showDialog() {
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.make_withdrawal_dialog, null)
        val mBuilder = activity?.let { it1 ->
            AlertDialog.Builder(it1)
                .setView(mDialogView)
        }
        //show dialog
        val mAlertDialog = mBuilder?.show()


    }
}