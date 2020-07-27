package com.mobileforce.hometeach.ui.home.student

import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.data.sources.remote.wrappers.Request
import com.mobileforce.hometeach.data.sources.remote.wrappers.StudentClass
import com.mobileforce.hometeach.databinding.ListItemClassOngoingParentDashBoardBinding
import com.mobileforce.hometeach.utils.loadImage
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class OngoingClassViewHolderStudentDashBoard(var binding: ListItemClassOngoingParentDashBoardBinding) :
    ViewHolder<StudentClass>(binding.root) {

    override fun bind(element: StudentClass) {
//        val currentDateTime = System.currentTimeMillis()
//        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH-mm", Locale.US)
//        val startRequestDateTime = dateFormat.parse(element.day + " " + element.from_hour + ":" + element.from_minute)!!.time
//        val endRequestDateTime = dateFormat.parse(element.day + " " + element.to_hour + ":" + element.to_minute)!!.time
        with(element) {
            binding.subjectName.text = subject
            binding.classProgressBar.progress = 0 //(currentDateTime/endRequestDateTime).toInt() * 100
            binding.classProgressText.text = binding.classProgressBar.progress.toString() + "%" + "\ndone"
            binding.tutorName.text = tutor_name
            binding.tutorSubject.text = "$subject Tutor"
            binding.tutorImage.loadImage(URL(tutor_pic))
//            if (startRequestDateTime < currentDateTime || currentDateTime < endRequestDateTime) {
//            }
        }
    }
}