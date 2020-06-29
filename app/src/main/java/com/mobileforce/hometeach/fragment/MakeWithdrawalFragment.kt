package com.mobileforce.hometeach.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import de.hdodenhof.circleimageview.CircleImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MakeWithdrawalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MakeWithdrawalFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_make_withdrawal, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow)
        }
        val amount = view.findViewById<EditText>(R.id.amount)
        val btn_cancel =view.findViewById<Button>(R.id.btn_cancel)
        val btn_confirm =view.findViewById<Button>(R.id.btn_confirm)
        val withdrawal_history = view.findViewById<RecyclerView>(R.id.withdrawal_history_recycler)
        val username = view.findViewById<TextView>(R.id.username)
        val balance = view.findViewById<TextView>(R.id.balance)
        val user_image = view.findViewById<CircleImageView>(R.id.user_image)

        btn_cancel.setOnClickListener {

        }

        btn_confirm.setOnClickListener {

        }

    }
}