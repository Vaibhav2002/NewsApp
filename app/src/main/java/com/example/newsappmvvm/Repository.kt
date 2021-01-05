package com.example.newsappmvvm

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.newsappmvvm.Remote.NewsApi
import com.example.newsappmvvm.local_db.NewsDatabase
import com.example.newsappmvvm.modelData.Article
import com.example.newsappmvvm.searchPage.SearchPagingSource
import com.example.newsappmvvm.trendingPage.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(private val database: NewsDatabase, private val api: NewsApi) {

    //remote data source functions
    fun getAllNews() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = true),
        pagingSourceFactory = { PagingSource(api) }).liveData


    fun searchNews(query: String) = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = true),
        pagingSourceFactory = { SearchPagingSource(api, query) }).liveData

    //local data source functions
    fun getAllSavedNews() = database.Dao.getAllArticles()

    suspend fun saveNewsIntoDB(article: Article) {
        withContext(Dispatchers.IO) {
            database.Dao.insert(article)
        }
    }

    suspend fun deleteNews(article: Article) {
        withContext(Dispatchers.IO) {
            database.Dao.deleteArticle(article)
        }
    }
}