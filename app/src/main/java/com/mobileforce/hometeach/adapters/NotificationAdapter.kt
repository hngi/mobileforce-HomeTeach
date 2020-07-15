package com.example.hometeach.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.models.Notifications
import kotlinx.android.synthetic.main.parent_notification_layout.view.*

import kotlin.collections.ArrayList

class NotificationAdapter (var  items: ArrayList<Notifications>, var clickListener: OnNotificationItemClickListener) : RecyclerView.Adapter<NotificationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        lateinit var  notificationViewHolder: NotificationsViewHolder

        notificationViewHolder = NotificationsViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_notifications, parent, false))
        return notificationViewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {

        holder.initialize(items.get(position), clickListener)

    }
}



class  NotificationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var NotificationTitle = itemView.Notification_Title
    var NotificationTime = itemView.Time_Tv
    var NotificationImage = itemView.Circle_Image
    var NotificationTag = itemView.Notification_Tag



    fun initialize (item: Notifications, action: OnNotificationItemClickListener){
        NotificationTitle.text = item.Title
        NotificationTime.text = item.Time.toString()
        NotificationTag.text = item.Tag
        NotificationImage.setImageResource(item.NotificationImage)



        itemView.setOnClickListener{
            action.onItemClick(item, adapterPosition)
        }

    }



}

interface OnNotificationItemClickListener{
    fun onItemClick(items: Notifications, position: Int)

}
