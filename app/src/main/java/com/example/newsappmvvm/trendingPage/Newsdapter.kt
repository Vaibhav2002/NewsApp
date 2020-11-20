package com.example.newsappmvvm.trendingPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.databinding.NewsItemBinding
import com.example.newsappmvvm.modelData.Article

class NewsAdapter(private val onItemClickListener: onItemClickListener) :
    PagingDataAdapter<Article, NewsAdapter.NewsViewHolder>(diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onItemClickListener) }
    }

    class NewsViewHolder private constructor(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): NewsViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = NewsItemBinding.inflate(inflater, parent, false)
                return NewsViewHolder(binding)
            }
        }

        fun bind(article: Article,onItemClickListener: onItemClickListener) {
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

interface onItemClickListener{
    fun onItemClick(article: Article)
}



