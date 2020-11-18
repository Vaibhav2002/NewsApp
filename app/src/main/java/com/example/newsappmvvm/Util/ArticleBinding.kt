package com.example.newsappmvvm.Util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.newsappmvvm.modelData.Article

@BindingAdapter("NewsImage")
fun ImageView.setNewsImage(article: Article?){
        article.let {
            Glide.with(context).load(article?.urlToImage).into(this)
        }
}