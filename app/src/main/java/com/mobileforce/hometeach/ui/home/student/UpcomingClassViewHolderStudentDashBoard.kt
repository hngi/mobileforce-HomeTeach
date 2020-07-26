package com.mobileforce.hometeach.ui.home.student

import android.util.Log
import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.data.sources.remote.wrappers.Request
import com.mobileforce.hometeach.databinding.ListItemClassUpcomingParentDashBoardBinding
import com.mobileforce.hometeach.utils.loadImage
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class UpcomingClassViewHolderStudentDashBoard(var binding: ListItemClassUpcomingParentDashBoardBinding) :
    ViewHolder<Request>(binding.root) {
    override fun bind(element: Request) {
//        val currentDateTime = System.currentTimeMillis()
//        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH-mm", Locale.US)
//        val requestDateTime = dateFormat.parse(element.day + " " + element.from_hour + ":" + element.from_minute)!!.time
        with(element) {
            binding.subjectName.text = subject
            binding.dateTime.text = "$day-$from_hour:$from_minute"
            binding.tutorName.text = tutor_name
            binding.tutorSubject.text = "$subject Tutor"
            binding.tutorImage.loadImage(URL(tutor_pic))
//            if (currentDateTime < requestDateTime) {
//            } else {
//                Log.d("TAG", "Time passed")
//            }
        }
    }
}