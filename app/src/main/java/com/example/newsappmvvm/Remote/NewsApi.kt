package com.example.newsappmvvm.Remote

import com.example.newsappmvvm.Util.API_KEY
import com.example.newsappmvvm.modelData.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getAllNews(
        @Query("country") country: String ="in",
        @Query("page") page:Int=1,
        @Query("apiKey") apikey: String = API_KEY):Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("apikey") apikey: String = API_KEY
    ):Response<NewsResponse>

}