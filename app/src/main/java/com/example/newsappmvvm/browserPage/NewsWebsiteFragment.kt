package com.example.newsappmvvm.browserPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.newsappmvvm.MainActivity
import com.example.newsappmvvm.databinding.FragmentNewsWebsiteBinding
import com.google.android.material.snackbar.Snackbar

class NewsWebsiteFragment : Fragment() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = (activity as MainActivity).viewModel
        val binding = FragmentNewsWebsiteBinding.inflate(inflater, container, false)
        val article = NewsWebsiteFragmentArgs.fromBundle(requireArguments()).article
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(article.url!!)
        }

        binding.saveNewsBtn.setOnClickListener {
            viewModel.saveNewsIntoDB(article)
            Snackbar.make(binding.root,"News Saved",Snackbar.LENGTH_SHORT).show()
        }
        return binding.root
    }
}