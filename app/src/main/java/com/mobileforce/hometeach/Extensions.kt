package com.mobileforce.hometeach

import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(image: Any, placeholder: Int = 0, circular: Boolean = false) {
    Glide
        .with(context)
        .load(image)
        .placeholder(placeholder)
        .apply(
            if (circular) {
                RequestOptions.circleCropTransform()
            } else {
                RequestOptions.noTransformation()
            }
        )
        .into(this)
}