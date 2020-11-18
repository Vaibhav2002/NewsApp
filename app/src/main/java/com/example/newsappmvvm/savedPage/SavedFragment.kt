package com.example.newsappmvvm.savedPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newsappmvvm.MainActivity
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentSavedBinding
import com.example.newsappmvvm.modelData.Article


class SavedFragment : Fragment(),onItemClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val binding=FragmentSavedBinding.inflate(inflater,container,false)
        val viewModel=(activity as MainActivity).viewModel
        val savedAdapter=SavedAdapter(this)
        binding.recycle.apply {
            setHasFixedSize(true)
            adapter=savedAdapter
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {
            savedAdapter.submitList(it)
        })
        return binding.root
    }


    override fun onItemClick(article: Article) {
    }
}