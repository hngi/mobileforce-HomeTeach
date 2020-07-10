package com.mobileforce.hometeach.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar

/**
 * Authored by enyason
 */

/**
 * load image into an image view
 * @param image can be a url,a drawable, an file resource id etc
 * @param placeholder is the default image that shows when actual image is being loaded or in case of an error
 * @param circular determines if a circular transformation is used to achieve a circular image view
 */
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

/**
 * inflate layout using layout inflater with context from the parent
 */
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

fun View.snack(message: String, lenth: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, lenth).show()
}