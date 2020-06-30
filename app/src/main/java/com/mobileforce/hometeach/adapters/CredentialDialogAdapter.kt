package com.mobileforce.hometeach.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.models.Credential

class CredentialDialogAdapter(docs: ArrayList<Credential>) :
    RecyclerView.Adapter<CredentialDialogAdapter.CredentialDialogViewHolder>() {

    private var docs: ArrayList<Credential> = ArrayList()
    private lateinit var onClickListener: View.OnClickListener

    fun setOnclickListener(onClickListener: View.OnClickListener){
        this.onClickListener = onClickListener
    }

    init {
        this.docs = docs
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CredentialDialogViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.credentials_list_view, parent, false)
        return CredentialDialogViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return docs.size
    }

    override fun onBindViewHolder(
        holder: CredentialDialogViewHolder,
        position: Int
    ) {
        val doc = docs[position]
        holder.bind(doc)
        holder.openText.setOnClickListener(onClickListener)
        holder.deleteText.setOnClickListener(onClickListener)
    }


    class CredentialDialogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private var docImage: ImageView = itemView.findViewById(R.id.iv_credentials_icon)
        private var docName: TextView = itemView.findViewById(R.id.tv_credentials_name)
        var openText: TextView = itemView.findViewById(R.id.tv_open_credentials)
        var deleteText: TextView = itemView.findViewById(R.id.tv_delete_credentials)

        init {
            openText.tag = this.openText
            deleteText.tag = this.deleteText
        }

        fun bind(doc: Credential){
            docImage.setImageResource(doc.docImage)
            docName.text = doc.docName
        }
    }
}