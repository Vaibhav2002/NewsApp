package com.example.newsappmvvm.local_db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.newsappmvvm.modelData.Article


@Dao
interface NewsDao {

    @Insert
    suspend fun insert(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)


}