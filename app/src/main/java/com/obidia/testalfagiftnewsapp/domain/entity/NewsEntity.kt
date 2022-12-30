package com.obidia.testalfagiftnewsapp.domain.entity

data class NewsEntity(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
)
