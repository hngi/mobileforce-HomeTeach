package com.mobileforce.hometeach.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.models.OngoingClassModelTutor

/**
 * Authored by enyason
 */
class HomePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ongoingClassesList: MutableList<OngoingClassModelTutor> = mutableListOf()
        ongoingClassesList.add(
            OngoingClassModelTutor(
                1,
                "Physics",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Rahman Django",
                "profile_image",
                40,
                1
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                2,
                "Biology",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Alice Snow",
                "profile_image",
                100,
                4
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                3,
                "Mathematics",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Alice Snow",
                "https://via.placeholder.com/300/09f/fff.png",
                100,
                3
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                4,
                "Chemistry",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Rahman Django",
                "profile_image",
                40,
                5
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                5,
                "Geography",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Alice Snow",
                "profile_image",
                100,
                1
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                6,
                "Computer Science",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Alice Snow",
                "https://via.placeholder.com/300/09f/fff.png",
                100,
                6
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                7,
                "Fishery",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Rahman Django",
                "profile_image",
                40,
                6
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                8,
                "Fine Arts",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Alice Snow",
                "profile_image",
                100,
                2
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                9,
                "Drama",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Alice Snow",
                "https://via.placeholder.com/300/09f/fff.png",
                100,
                6
            )
        )

    }

}