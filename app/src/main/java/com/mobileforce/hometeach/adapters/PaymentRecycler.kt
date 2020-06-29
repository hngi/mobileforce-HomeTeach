package com.mobileforce.hometeach.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.ListItemClassBinding
import com.mobileforce.hometeach.databinding.WithdrawalListItemBinding
import com.mobileforce.hometeach.models.OngoingClassModel
import com.mobileforce.hometeach.models.Payment

class PaymentRecycler : ListAdapter<Payment, PaymentViewHolder>(DiffClass) {

    companion object DiffClass : DiffUtil.ItemCallback<Payment>() {
        override fun areItemsTheSame(
            oldItem: Payment,
            newItem: Payment
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Payment,
            newItem: Payment
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder(WithdrawalListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = getItem(position)
        holder.bind(payment)
    }


}

class PaymentViewHolder(private val binding: WithdrawalListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(pay: Payment) {
        binding.pay = pay
        binding.executePendingBindings()
    }

}