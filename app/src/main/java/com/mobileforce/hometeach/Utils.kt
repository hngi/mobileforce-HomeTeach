package com.mobileforce.hometeach

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mobileforce.hometeach.adapters.CircleTransform
import com.squareup.picasso.Picasso

@BindingAdapter("imagecircular")
fun ImageView.bindTutorImage(tutorImage:String){
    tutorImage.let {
        Picasso.get().load(tutorImage).transform(CircleTransform()).placeholder(R.drawable.profile_image).error(R.drawable.profile_image).into(this)
    }
}