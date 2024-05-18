package com.obidia.testalfagiftnewsapp.domain.repo

import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity
import com.obidia.testalfagiftnewsapp.domain.entity.SourcesEntity
import com.obidia.testalfagiftnewsapp.utils.result.ResponseResult
import kotlinx.coroutines.flow.Flow

interface  Repository {
    suspend fun getSources(category: String): Flow<ResponseResult<MutableList<SourcesEntity>>>

    suspend fun getNews(
        sources: String
    ): Flow<ResponseResult<MutableList<NewsEntity>>>

    suspend fun getAllSources(): Flow<ResponseResult<MutableList<SourcesEntity>>>

    suspend fun getSearchNews(query: String): Flow<ResponseResult<MutableList<NewsEntity>>>
}