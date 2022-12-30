package com.obidia.testalfagiftnewsapp.data.model.response


import com.google.gson.annotations.SerializedName
import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity

data class NewsResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("articles")
    val articles: List<Article?>?
)

data class Article(
    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
) {
    fun toNewsEntity() = NewsEntity(
        author ?: "",
        title ?: "",
        description ?: "",
        url ?: "",
        urlToImage ?: "",
        publishedAt ?: ""
    )
}