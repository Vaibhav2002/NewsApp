package com.example.newsappmvvm.savedPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.MainActivity
import com.example.newsappmvvm.MainViewModel
import com.example.newsappmvvm.databinding.FragmentSavedBinding
import com.example.newsappmvvm.modelData.Article
import com.google.android.material.snackbar.Snackbar


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
        Log.i("SavedFragVM", viewModel.toString())
        savedAdapter = SavedNewsAdapter(this)
        binding.recyclerView.adapter = savedAdapter
        viewModel.getAllSavedNews().observe(viewLifecycleOwner, Observer {
            Log.i("list", it.toString())
            savedAdapter.submitList(it)
        })

        val itemTouchHelper = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val article = savedAdapter.currentList[viewHolder.absoluteAdapterPosition]
                viewModel.deleteNews(article)
                Snackbar.make(binding.root, "News deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveNewsIntoDB(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerView)
        return binding.root
    }


    override fun onItemClick(article: Article) {
        findNavController().navigate(
            SavedFragmentDirections.actionSavedFragmentToNewsWebsiteFragment(
                article
            )
        )
    }
}