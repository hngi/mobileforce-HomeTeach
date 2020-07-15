package com.mobileforce.hometeach.ui.home.student

import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.databinding.ListItemClassOngoingParentDashBoardBinding
import com.mobileforce.hometeach.utils.loadImage
import com.mobileforce.hometeach.models.OngoingClassModelTutor


class OngoingClassViewHolderStudentDashBoard(var binding: ListItemClassOngoingParentDashBoardBinding) :
    ViewHolder<OngoingClassModelTutor>(binding.root) {
    override fun bind(element: OngoingClassModelTutor) {

        with(element) {

            binding.subjectName.text = subject
            binding.tutorName.text = tutorName
            binding.tutorImage.loadImage(tutorImage, R.drawable.profile_image)
        }

    }

}