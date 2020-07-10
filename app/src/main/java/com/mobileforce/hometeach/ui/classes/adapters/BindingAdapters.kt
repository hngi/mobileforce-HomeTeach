package com.mobileforce.hometeach.ui.classes.adapters

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.utils.showIf

/**
 * Created by Mayokun Adeniyi on 28/06/2020.
 */

@BindingAdapter("showButton")
fun Button.show(state: Boolean){
    this.showIf { state }
}

@BindingAdapter("showText")
fun TextView.show(state: Boolean){
    this.showIf { !state }
}

@BindingAdapter("setColor")
fun TextView.setColor(status: String){
    if (status.contains("Awaiting Approval",ignoreCase = true)){
        this.setTextColor(resources.getColor(R.color.primaryDarkColor))
    }else if (status.contains("Approved",ignoreCase = true)){
        this.setTextColor(resources.getColor(R.color.primaryColor))
    }
}