package com.mobileforce.hometeach.ui.withdrawalscreens.mybank
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
import com.mobileforce.hometeach.databinding.FragmentTutorAddBankBinding


class TutorAddBankFragment : Fragment() {
    lateinit var navController: NavController
       private lateinit var binding: FragmentTutorAddBankBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentTutorAddBankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val toolbar = binding.toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow)
        }
        val accName = binding.accName
        val txtAccName = accName.text
        val accNumber =    binding.accNumber
        val txtAccNumber = accNumber.text
        val accRoute = binding.accRoute
        val txtAccRoute = accRoute.text
        val ssNumber = binding.accSecurityNumber
        val txtSsNumber = ssNumber.text
        val btnSave =binding.save

        toolbar.setNavigationOnClickListener {

            navController.navigate(R.id.myBanks)
        }

        btnSave.setOnClickListener {

            if (txtAccName.isNullOrEmpty())
            {

            }
            if (txtAccNumber.isNullOrEmpty())
            {

            }
            if (txtAccRoute.isNullOrEmpty())
            {

            }
            if (txtSsNumber.isNullOrEmpty())
            {

            }

            showDialog()
        }

    }

    private fun showDialog() {
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.save_bank_dialog, null)
        val mBuilder = activity?.let { it1 ->
            AlertDialog.Builder(it1)
                .setView(mDialogView)
        }
        //show dialog
        val mAlertDialog = mBuilder?.show()


    }
}