package com.example.newsappmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappmvvm.modelData.Article
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repository) : ViewModel() {

    private var _trendingNews: MutableLiveData<List<Article>> = MutableLiveData()
    private var _searchNews: MutableLiveData<List<Article>> = MutableLiveData()
    val trendingNews: LiveData<List<Article>> get() = _trendingNews
    val searchNews: LiveData<List<Article>> get() = _searchNews

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

    fun searchNews(query: String) {
        viewModelScope.launch {
            val response = repo.searchNews(query)
            if (response.isSuccessful && response.body() != null)
                _searchNews.value = response.body()!!.articles
        }
    }

    fun getAllSavedNews()=repo.getAllSavedNews()

    fun saveNewsIntoDB(article: Article) {
        viewModelScope.launch {
            repo.saveNewsIntoDB(article)
        }
    }

    fun deleteNews(article: Article) {
        viewModelScope.launch {
            repo.deleteNews(article)
        }
    }

    fun clearSearchData() {
        _searchNews.value = emptyList()
    }


}