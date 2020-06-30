package com.mobileforce.hometeach.TutorDashBoardFragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mobileforce.hometeach.R
import de.hdodenhof.circleimageview.CircleImageView

class CardDetails : Fragment() {

    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow)
        }
        val username = view.findViewById<TextView>(R.id.username)
        val user_image = view.findViewById<CircleImageView>(R.id.user_image)
        val btn_cancel =view.findViewById<Button>(R.id.btn_cancel)
        val add_card = view.findViewById<LinearLayout>(R.id.add_card)

        toolbar.setNavigationOnClickListener {

            navController.navigate(R.id.tutorHomePageFragment)
        }

        add_card.setOnClickListener {
            navController.navigate(R.id.addCardDetails)
        }
    }
}