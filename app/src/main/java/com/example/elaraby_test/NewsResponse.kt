package com.example.elaraby_test

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)