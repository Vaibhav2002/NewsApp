package com.example.newsappmvvm.searchPage

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.newsappmvvm.MainActivity
import com.example.newsappmvvm.MainViewModel
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentSearchBinding
import com.example.newsappmvvm.modelData.Article


private const val TAG = "SearchFragment"

class SearchFragment : Fragment(R.layout.fragment_search), onItemClickListener {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var viewModel: MainViewModel
    var boot = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boot = false
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        searchAdapter = SearchAdapter(this)
        binding.recycle.apply {
            adapter = searchAdapter
            setHasFixedSize(false)
        }
        viewModel.searchNews.observe(viewLifecycleOwner, Observer {
            searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        searchAdapter.addLoadStateListener { loadState ->
            binding.apply {
                recycle.isVisible = loadState.source.refresh is LoadState.NotLoading && boot
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading && boot
                binding.noResult.isVisible =
                    (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached
                            && searchAdapter.itemCount < 1)
            }
        }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.query.postValue(query)
                } else {
                    searchAdapter.submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
                }
                val imm =
                    context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                boot = true
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    binding.recycle.isVisible = false
                    searchAdapter.submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
                    binding.noResult.isVisible = false
                }
                return true
            }
        })
    }

    override fun onItemClick(article: Article) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToNewsWebsiteFragment(
                article
            )
        )
    }
}