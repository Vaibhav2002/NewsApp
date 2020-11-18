package com.example.newsappmvvm.savedPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newsappmvvm.MainActivity
import com.example.newsappmvvm.MainViewModel
import com.example.newsappmvvm.databinding.FragmentSavedBinding
import com.example.newsappmvvm.modelData.Article


class SavedFragment : Fragment(), onItemClickListener {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var savedAdapter: SavedNewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel
        Log.i("SavedFragVM",viewModel.toString())
        savedAdapter= SavedNewsAdapter(this)
        binding.recyclerView.adapter = savedAdapter
        viewModel.getAllSavedNews().observe(viewLifecycleOwner, Observer {
            Log.i("list",it.toString())
            savedAdapter.submitList(it)
        })
        return binding.root
    }


    override fun onItemClick(article: Article) {
        val action =
            SavedFragmentDirections.actionSavedFragmentToNewsWebsiteFragment(article = article)
        findNavController().navigate(action)
    }
}