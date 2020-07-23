package com.mobileforce.hometeach.models

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mobileforce.hometeach.adapters.CircleTransform
import com.mobileforce.hometeach.R
import com.squareup.picasso.Picasso
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TutorModel(
    val integerId: Int,
    val id: String,
    val full_name: String,
    val profile_pic: String?,
    val description: String?,
    val tutorSubject: String?,
    val hourly_rate: String?,
    var rating: Double?
) : Parcelable


@BindingAdapter("tutor_image")
fun setEventImage(imageView: ImageView, url: String?) {
    Picasso.get().load(url).transform(CircleTransform()).placeholder(R.drawable.profile_image)
        .error(R.drawable.profile_image).into(imageView)
}