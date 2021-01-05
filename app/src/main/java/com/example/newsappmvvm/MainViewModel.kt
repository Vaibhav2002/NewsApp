package com.example.newsappmvvm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsappmvvm.modelData.Article
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val repo: Repository) : ViewModel() {

    private var _trendingNews: LiveData<PagingData<Article>> = MutableLiveData()

    val trendingNews get() = _trendingNews
    val searchNews get() = _searchNews

    val query: MutableLiveData<String> = MutableLiveData()
    private var _searchNews: LiveData<PagingData<Article>> = query.switchMap {
        repo.searchNews(query.value.toString()).cachedIn(viewModelScope)
    }

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            _trendingNews = repo.getAllNews().cachedIn(viewModelScope)
        }

    }

    fun getAllSavedNews() = repo.getAllSavedNews()

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

}