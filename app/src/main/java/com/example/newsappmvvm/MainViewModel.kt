package com.example.newsappmvvm

import androidx.lifecycle.*
import com.example.newsappmvvm.local_db.NewsDatabase
import com.example.newsappmvvm.modelData.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repo: Repository) : ViewModel() {

    private var _trendingNews: MutableLiveData<List<Article>> = MutableLiveData()
    private var _searchNews:MutableLiveData<List<Article>> = MutableLiveData()
    private var _savedNews:MutableLiveData<List<Article>> = MutableLiveData()
    val trendingNews get() = _trendingNews
    val searchNews get() = _searchNews
    val savedNews get() = _savedNews
    init {
        getData()
    }
    fun getData() {
        viewModelScope.launch {
            val response = repo.getAllNews()
            if (response.isSuccessful && response.body() != null)
                _trendingNews.value = response.body()!!.articles
        }
    }

    fun searchNews(query:String){
        viewModelScope.launch {
            val response=repo.searchNews(query)
            if(response.isSuccessful&&response.body()!=null)
                _searchNews.value=response.body()!!.articles
        }
    }

     fun getAllSavedNews(){
        viewModelScope.launch {
            _savedNews.value=repo.getAllSavedNews().value
        }
    }

     fun saveNewsIntoDB(article: Article){
        viewModelScope.launch {
            repo.saveNewsIntoDB(article)
        }
    }
     fun deleteNews(article: Article){
        viewModelScope.launch {
            repo.deleteNews(article)
        }
    }


}