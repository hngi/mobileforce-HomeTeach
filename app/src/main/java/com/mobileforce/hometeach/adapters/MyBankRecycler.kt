package com.mobileforce.hometeach.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.MyBanklistItemBinding
import com.mobileforce.hometeach.models.MyBank
import com.mobileforce.hometeach.models.Payment

class MyBankRecycler : ListAdapter<MyBank, MyBanksViewHolder>(DiffClass) {

    companion object DiffClass : DiffUtil.ItemCallback<MyBank>() {
        override fun areItemsTheSame(
            oldItem: MyBank,
            newItem: MyBank
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MyBank,
            newItem: MyBank
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBanksViewHolder {
        return MyBanksViewHolder(MyBanklistItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MyBanksViewHolder, position: Int) {
        val bank = getItem(position)
        holder.bind(bank)
    }


}

class MyBanksViewHolder(private val binding: MyBanklistItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(bank: MyBank) {
        binding.bank = bank
        binding.executePendingBindings()
    }

}