package com.mobileforce.hometeach.ui.studentpayments.makepayments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.StudentsPaymentListBinding
import com.mobileforce.hometeach.ui.studentpayments.Payment


class StudentPaymentsRecycler : ListAdapter<Payment, StudentPaymentsHolder>(
    DiffClass
) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentPaymentsHolder {
        return StudentPaymentsHolder(
            StudentsPaymentListBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: StudentPaymentsHolder, position: Int) {
        val payment = getItem(position)
        //holder.bind(payment)
    }
}

class StudentPaymentsHolder(private val binding: StudentsPaymentListBinding) :
    RecyclerView.ViewHolder(binding.root) {
//    fun bind(pay: Payment) {
//        binding.pay = pay
//        binding.executePendingBindings()
//    }

}