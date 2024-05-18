package com.obidia.testalfagiftnewsapp.presentation.detailnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.obidia.testalfagiftnewsapp.databinding.FragmentDetailArticleBinding

class DetailArticleFragment : Fragment() {
    private lateinit var binding: FragmentDetailArticleBinding
    private val args by navArgs<DetailArticleFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val client = object : WebViewClient() {

            // ProgressBar will disappear once page is loaded
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                binding.paginationProgressBar.visibility = View.GONE
            }
        }
        binding.webView.apply {
            webViewClient = client
            loadUrl(args.url)
        }
    }

}