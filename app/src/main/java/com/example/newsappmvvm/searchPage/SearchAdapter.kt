package com.example.newsappmvvm.searchPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.databinding.NewsItemBinding
import com.example.newsappmvvm.modelData.Article

class SearchAdapter(private val onItemClickListener: onItemClickListener) :
    ListAdapter<Article, SearchAdapter.SearchNewsViewHolder>(diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchNewsViewHolder {
        return SearchNewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchNewsViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }

    class SearchNewsViewHolder private constructor(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): SearchNewsViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = NewsItemBinding.inflate(inflater, parent, false)
                return SearchNewsViewHolder(binding)
            }
        }

        fun bind(article: Article, onItemClickListener: onItemClickListener) {
            binding.article = article
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(article)
            }
        }

    }


}

class diff : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}

interface onItemClickListener {
    fun onItemClick(article: Article)
}
