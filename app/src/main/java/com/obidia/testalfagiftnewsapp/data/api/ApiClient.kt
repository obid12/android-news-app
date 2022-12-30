package com.obidia.testalfagiftnewsapp.data.api

import com.obidia.testalfagiftnewsapp.data.model.response.NewsResponse
import com.obidia.testalfagiftnewsapp.data.model.response.SourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("category")
        category: String
    ): Response<SourcesResponse>

    @GET("top-headlines/sources")
    suspend fun getAllSources(
    ): Response<SourcesResponse>

    @GET("top-headlines")
    suspend fun getNews(
        @Query("sources")
        sources: String
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String
    ): Response<NewsResponse>
}