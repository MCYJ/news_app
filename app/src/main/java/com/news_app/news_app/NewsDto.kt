package com.news_app.news_app

data class NewsDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)