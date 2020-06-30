package com.mobileforce.hometeach.ui.home


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.OnItemtouch
import com.mobileforce.hometeach.adapters.TutorClassesAdapter
import com.mobileforce.hometeach.models.OngoingClassModelTutor
import com.mobileforce.hometeach.models.TutorClassesDataModel
import kotlinx.android.synthetic.main.fragment_home_page_tutor.*
import java.util.*

/**
 * Authored by enyason
 */
class HomePageFragment : Fragment() {
    private lateinit var tutor_classes_list: List<TutorClassesDataModel>
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}


