package com.news_app.news_app

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/top-headlines/sources")
    fun getNews(
        @Query("apiKey") API_KEY: String
    ): Call<NewsDto>
}