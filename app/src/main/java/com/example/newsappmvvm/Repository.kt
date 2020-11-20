package com.example.newsappmvvm

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.newsappmvvm.Remote.Retrofit
import com.example.newsappmvvm.local_db.NewsDatabase
import com.example.newsappmvvm.modelData.Article
import com.example.newsappmvvm.modelData.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class Repository(context: Context) {
    private var database: NewsDatabase = NewsDatabase.getInstance(context)

    //remote data source functions
    fun getAllNews() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { PagingSource(Retrofit.api) }).liveData


    suspend fun searchNews(query: String): Response<NewsResponse> {
        return withContext(Dispatchers.IO) {
            Retrofit.api.searchNews(query = query)
        }
    }

    //local data source functions
    fun getAllSavedNews() = database.Dao.getAllArticles()

    suspend fun saveNewsIntoDB(article: Article){
        withContext(Dispatchers.IO){
            database.Dao.insert(article)
        }
    }
    suspend fun deleteNews(article: Article){
        withContext(Dispatchers.IO){
            database.Dao.deleteArticle(article)
        }
    }
}