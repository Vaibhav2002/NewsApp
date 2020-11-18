package com.example.newsappmvvm.local_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsappmvvm.modelData.Article


@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article) : Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)


}