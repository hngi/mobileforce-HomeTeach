package com.mobileforce.hometeach.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentNotificationTutorBinding




lateinit var notificationTutorBinding: FragmentNotificationTutorBinding



class NotificationTutorFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        notificationTutorBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_notification_tutor,container , false)

        return notificationTutorBinding.root
    }


}