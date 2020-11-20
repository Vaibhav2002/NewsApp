package com.example.newsappmvvm.trendingPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newsappmvvm.MainActivity
import com.example.newsappmvvm.MainViewModel
import com.example.newsappmvvm.databinding.FragmentTrendingBinding
import com.example.newsappmvvm.modelData.Article

class TrendingFragment : Fragment(), onItemClickListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentTrendingBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel
        adapter = NewsAdapter(this)
        binding.recycle.adapter = adapter
        binding.recycle.setHasFixedSize(true)
        viewModel.trendingNews.observe(viewLifecycleOwner, Observer {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = true
            viewModel.getData()
            binding.refresh.isRefreshing = false
        }
        return binding.root
    }

    override fun onItemClick(article: Article) {
        findNavController().navigate(
            TrendingFragmentDirections.actionTrendingFragmentToNewsWebsiteFragment(
                article = article
            )
        )
    }
}