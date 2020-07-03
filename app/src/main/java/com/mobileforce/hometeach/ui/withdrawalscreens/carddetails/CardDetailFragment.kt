package com.mobileforce.hometeach.ui.withdrawalscreens.carddetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.CircleTransform
import com.mobileforce.hometeach.databinding.FragmentCardDetailBinding
import com.mobileforce.hometeach.ui.withdrawalscreens.MyCard
import com.mobileforce.hometeach.ui.withdrawalscreens.MyCardModel
import com.squareup.picasso.Picasso

// Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CarddetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardDetailFragment : Fragment() {
    // Rename and change types of parameters
    private lateinit var cardList:MutableList<MyCard>
    private lateinit var binding: FragmentCardDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardDetailBinding.inflate(inflater, container, false)

        return binding.root
       // return inflater.inflate(R.layout.fragment_card_detail, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardList = mutableListOf()
        cardList.add(
            MyCard(
                1,
                R.drawable.ic_visa,
                "....2019",
                true
            )
        )
        cardList.add(
            MyCard(
                2,
                R.drawable.ic_master,
                "....3200",
                false
            )
        )

        var cards = MyCardModel(
            1,
            cardList,
            "Rahman Django",
            "profile_image",
            "215000 N"
        )
        binding.tutorName.text = cards.tutorName
        binding.tutorBalance.text = "Balance: "+cards.balance
        Picasso.get().load("profile_image").transform(CircleTransform()).placeholder(R.drawable.profile_image).error(R.drawable.profile_image).into(binding.tutorImage)


        val adapter =
            MyCardRecycler()
        adapter.submitList(cards.cards)
        binding.mycardsRecyclerView.adapter = adapter
        binding.mycardsRecyclerView.hasFixedSize()
    }

}

@BindingAdapter("card_image")
fun setEventImage(imageView: ImageView, url: Int) {
    Picasso.get().load(url).placeholder(R.drawable.ic_master)
        .error(R.drawable.ic_master).into(imageView)
}
