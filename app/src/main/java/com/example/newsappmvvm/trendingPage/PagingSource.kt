package com.example.newsappmvvm.trendingPage

import androidx.paging.PagingSource
import com.example.newsappmvvm.Remote.NewsApi
import com.example.newsappmvvm.modelData.Article
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE = 1

class PagingSource(private val api: NewsApi) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val response = api.getAllNews(page = page)
            val news = response.body()!!.articles
            LoadResult.Page(
                data = news,
                prevKey = if (page == STARTING_PAGE) null else page - 1,
                nextKey = if (news.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: NullPointerException) {
            LoadResult.Error(exception)
        }
    }
}