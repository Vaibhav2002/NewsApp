package com.example.newsappmvvm.searchPage

import android.content.Context
import android.hardware.input.InputManager
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newsappmvvm.MainActivity
import com.example.newsappmvvm.MainViewModel
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentSearchBinding
import com.example.newsappmvvm.modelData.Article
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment(), onItemClickListener {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var viewModel: MainViewModel
    private var job:Job?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel
        searchAdapter = SearchAdapter(this)
        binding.recycle.apply {
            adapter = searchAdapter
            setHasFixedSize(true)
        }
        viewModel.searchNews.observe(viewLifecycleOwner, Observer {
            searchAdapter.submitList(it)
        })
        binding.search.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                job?.cancel()
                job=MainScope().launch{
                    delay(500L)
                    newText.let {
                        if(newText?.isNotEmpty()!!)
                            viewModel.searchNews(newText)
                    }
                }
                return true
            }
        })
        return binding.root
    }

    override fun onItemClick(article: Article) {

    }
}