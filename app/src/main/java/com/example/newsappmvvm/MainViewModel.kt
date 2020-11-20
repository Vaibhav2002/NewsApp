package com.example.newsappmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsappmvvm.modelData.Article
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repository) : ViewModel() {

    private var _trendingNews: LiveData<PagingData<Article>> = MutableLiveData()
    private var _searchNews: MutableLiveData<List<Article>> = MutableLiveData()
    val trendingNews: LiveData<PagingData<Article>> get() = _trendingNews
    val searchNews: LiveData<List<Article>> get() = _searchNews

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            _trendingNews = repo.getAllNews().cachedIn(viewModelScope)
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