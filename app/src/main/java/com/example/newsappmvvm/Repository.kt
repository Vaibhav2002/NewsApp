package com.example.newsappmvvm

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsappmvvm.Remote.Retrofit
import com.example.newsappmvvm.local_db.NewsDatabase
import com.example.newsappmvvm.modelData.Article
import com.example.newsappmvvm.modelData.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class Repository(context: Context) {
    private var database:NewsDatabase = NewsDatabase.getInstance(context)

    //remote data source functions
    suspend fun getAllNews(): Response<NewsResponse> {
        return withContext(Dispatchers.IO) {
            Retrofit.api.getAllNews()
        }
    }

    suspend fun searchNews(query:String):Response<NewsResponse>{
        return withContext(Dispatchers.IO){
            Retrofit.api.searchNews(query = query)
        }
    }

    //local data source functions
     fun getAllSavedNews()= database.Dao.getAllArticles()

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