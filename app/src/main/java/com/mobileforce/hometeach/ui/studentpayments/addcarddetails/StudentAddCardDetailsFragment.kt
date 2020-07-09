package com.mobileforce.hometeach.ui.studentpayments.addcarddetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.CircleTransform
import com.mobileforce.hometeach.databinding.FragmentStudentAddCardDetailsBinding
import com.mobileforce.hometeach.databinding.FragmentStudentCardDetailsBinding
import com.mobileforce.hometeach.ui.studentpayments.StudentCard
import com.mobileforce.hometeach.ui.studentpayments.StudentCardModel
import com.mobileforce.hometeach.ui.studentpayments.makepayments.StudentCardsRecycler
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudentAddCardDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentAddCardDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentStudentAddCardDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentAddCardDetailsBinding.inflate(inflater, container, false)

        return binding.root //inflater.inflate(R.layout.fragment_student_add_card_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tutorName.text = "John Doe"
        binding.tutorBalance.text = "Balance: "+"215000 N"
        binding.addCardDetailsBtn.setOnClickListener {
            val mDialogView = LayoutInflater.from(activity).inflate(R.layout.add_card_dialog, null)
            val mBuilder = activity?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)

            }
            //show dialog
            val  mAlertDialog = mBuilder?.show()
        }
        Picasso.get().load("profile_image").transform(CircleTransform()).placeholder(R.drawable.profile_image).error(R.drawable.profile_image).into(binding.tutorImage)




    }

}