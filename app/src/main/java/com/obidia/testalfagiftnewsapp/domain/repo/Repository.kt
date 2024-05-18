package com.obidia.testalfagiftnewsapp.domain.repo

import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity
import com.obidia.testalfagiftnewsapp.domain.entity.SourcesEntity
import com.obidia.testalfagiftnewsapp.utils.result.Resource
import kotlinx.coroutines.flow.Flow

interface  Repository{
    suspend fun getSourcs(category: String): Flow<Resource<MutableList<SourcesEntity>>>
    suspend fun getNews(
        sources: String
    ): Flow<Resource<MutableList<NewsEntity>>>

    suspend fun getAllSources(): Flow<Resource<MutableList<SourcesEntity>>>

    suspend fun getSearchNews(query: String): Flow<Resource<MutableList<NewsEntity>>>
}