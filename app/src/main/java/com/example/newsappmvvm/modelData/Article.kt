package com.example.newsappmvvm.modelData

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "articles")
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @PrimaryKey
    val title: String,
    val url: String?,
    val urlToImage: String?
) :Serializable