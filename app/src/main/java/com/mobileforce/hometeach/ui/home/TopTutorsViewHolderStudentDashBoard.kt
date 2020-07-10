package com.mobileforce.hometeach.ui.home

import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.databinding.ListItemTutorParentDashBoardBinding
import com.mobileforce.hometeach.utils.loadImage
import com.mobileforce.hometeach.models.TopTutorModel


class TopTutorsViewHolderStudentDashBoard(var binding: ListItemTutorParentDashBoardBinding) :
    ViewHolder<TopTutorModel>(binding.root) {
    override fun bind(element: TopTutorModel) {

        with(element) {

            binding.textViewSubjectAndRating.text = tutorSubject
            binding.aboutTutor.text = tutorBio
            binding.tutorName.text = name
            binding.tutorImage.loadImage(tutorImage, R.drawable.profile_image)
        }

    }

}