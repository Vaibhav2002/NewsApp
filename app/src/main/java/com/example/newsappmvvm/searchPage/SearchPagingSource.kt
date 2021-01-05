package com.example.newsappmvvm.searchPage

import androidx.paging.PagingSource
import com.example.newsappmvvm.Remote.NewsApi
import com.example.newsappmvvm.modelData.Article

private const val START = 1

class SearchPagingSource(private val newsApi: NewsApi, private val query: String) :
    PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val key = params.key ?: START
        return try {
            val response = newsApi.searchNews(query, key)
            val article = response.body()!!.articles
            LoadResult.Page(
                data = article,
                prevKey = if (key == START) null else key - 1,
                nextKey = if (article.isEmpty()) null else key + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}