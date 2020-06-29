package com.mobileforce.hometeach

import android.view.View
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

/**
 * This function helps to toggle the visibility of a [View]. If the condition
 * is met, the [View] is made visible else it is hidden.
 */
inline fun <T : View> T.showIf(condition: (T) -> Boolean) {
    visibility = if (condition(this)) {
        View.VISIBLE
    } else {
        View.GONE
    }
}