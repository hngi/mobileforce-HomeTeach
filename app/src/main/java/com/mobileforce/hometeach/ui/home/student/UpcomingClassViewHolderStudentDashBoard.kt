package com.mobileforce.hometeach.ui.home.student

import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.databinding.ListItemClassUpcomingParentDashBoardBinding
import com.mobileforce.hometeach.utils.loadImage
import com.mobileforce.hometeach.models.UpComingClassModel


class UpcomingClassViewHolderStudentDashBoard(var binding: ListItemClassUpcomingParentDashBoardBinding) :
    ViewHolder<UpComingClassModel>(binding.root) {
    override fun bind(element: UpComingClassModel) {

        with(element) {

            binding.subjectName.text = subject
            binding.tutorName.text = tutorName
            binding.tutorImage.loadImage(tutorImage, R.drawable.profile_image)
        }

    }

}