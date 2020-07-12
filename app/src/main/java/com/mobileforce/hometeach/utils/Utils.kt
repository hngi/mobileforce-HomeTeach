package com.mobileforce.hometeach.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.CircleTransform
import com.squareup.picasso.Picasso
import java.lang.reflect.Method

@BindingAdapter("imagecircular")
fun ImageView.bindTutorImage(tutorImage:String){
    tutorImage.let {
        Picasso.get().load(tutorImage).transform(CircleTransform()).placeholder(R.drawable.profile_image).error(
            R.drawable.profile_image).into(this)
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

fun pojo2Map(obj: Any): Map<String, Any> {
    val hashMap: MutableMap<String, Any> =
        HashMap()
    try {
        val c: Class<out Any> = obj.javaClass
        val m: Array<Method> = c.methods
        for (i in m.indices) {
            if (m[i].name.indexOf("get") === 0) {
                val name: String =
                    m[i].name.toLowerCase().substring(3, 4) + m[i].getName()
                        .substring(4)
                hashMap[name] = m[i].invoke(obj, arrayOfNulls<Any>(0))
            }
        }
    } catch (e: Throwable) {
        //log error
    }
    return hashMap
}