package com.mobileforce.hometeach.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentCredentialBinding
import kotlinx.android.synthetic.main.fragment_credential.*


class CredentialFragment : Fragment() {
    lateinit var navController: NavController
    private lateinit var binding:FragmentCredentialBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCredentialBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credential, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val url = arguments?.getString("pdfUrl")
            url?.let { showPdf(it) }
    }

    fun showPdf(link:String)
    {
        webview.webViewClient =  WebViewClient()
        webview.settings.setSupportZoom(true)
       webview.settings.javaScriptEnabled = true
       webview.loadUrl(link)
    }

}