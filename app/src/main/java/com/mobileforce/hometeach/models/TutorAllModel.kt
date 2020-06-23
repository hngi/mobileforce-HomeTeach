package com.mobileforce.hometeach.models

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.hometeach.adapter.CircleTransform
import com.mobileforce.hometeach.R
import com.squareup.picasso.Picasso

data class TutorAllModel(

    val tutorName: String,
    val tutorImage:String,
    val description: String,
    val tutorSubject: String,
    val perhouramount:Int,
    var rating: String) {


}
@BindingAdapter("tutor_image")
fun setEventImage(imageView: ImageView, url: String?) {
    Picasso.get().load(url).transform(CircleTransform()).placeholder(R.drawable.profile_image)
        .error(R.drawable.profile_image).into(imageView)
}