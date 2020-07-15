package com.mobileforce.hometeach.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.CredentialDialogAdapter
import com.mobileforce.hometeach.models.Credential

class CredentialDialog : DialogFragment() {

    private var docs: ArrayList<Credential> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var onClickListener: View.OnClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_credentials, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addMoreIcon = view.findViewById<ImageView>(R.id.iv_add_more)
        val addMoreText = view.findViewById<TextView>(R.id.tv_add_more)
        val closeBtn = view.findViewById<MaterialButton>(R.id.bt_close_credentials)
        addMoreIcon.setOnClickListener {
            Toast.makeText(context, "More docs added", Toast.LENGTH_SHORT).show()
        }
        addMoreText.setOnClickListener {
            Toast.makeText(context, "More docs added", Toast.LENGTH_SHORT).show()
        }
        closeBtn.setOnClickListener {
            dismiss()
        }
        setupData()
        onClickListener = View.OnClickListener {
            if (it.id == R.id.tv_open_credentials){
                val openDoc = it.tag as TextView
                Toast.makeText(context, "Clicked " + openDoc.text, Toast.LENGTH_SHORT).show()

            } else if (it.id == R.id.tv_delete_credentials){
                val deleteDoc = it.tag as TextView
                Toast.makeText(context, "Clicked " + deleteDoc.text, Toast.LENGTH_SHORT).show()
            }
        }

        recyclerView = view.findViewById(R.id.rv_credentials)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        val adapter = CredentialDialogAdapter(docs)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.setOnclickListener(onClickListener)
    }

    private fun setupData(){
        val doc1 = Credential()
        doc1.docImage = R.drawable.pdf_icon
        doc1.docName = getString(R.string.rahman_djago_cv)
        docs.add(doc1)
        val doc2 = Credential()
        doc2.docImage = R.drawable.pdf_icon
        doc2.docName = getString(R.string.rahman_djago_letter)
        docs.add(doc2)
        val doc3 = Credential()
        doc3.docImage = R.drawable.pdf_icon
        doc3.docName = getString(R.string.rahman_djago_resume)
        docs.add(doc3)
    }
    companion object{
        fun newInstance(): CredentialDialog{
            return CredentialDialog()
        }
    }
}