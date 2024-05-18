package com.obidia.testalfagiftnewsapp.domain.usecase

import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity
import com.obidia.testalfagiftnewsapp.domain.entity.SourcesEntity
import com.obidia.testalfagiftnewsapp.domain.repo.Repository
import com.obidia.testalfagiftnewsapp.utils.result.ResponseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun getSources(category: String):
            Flow<ResponseResult<MutableList<SourcesEntity>>> {
        return repository.getSources(category)
    }

    suspend fun getAllSources(): Flow<ResponseResult<MutableList<SourcesEntity>>> {
        return repository.getAllSources()
    }

    suspend fun getAllNews(sources: String): Flow<ResponseResult<MutableList<NewsEntity>>> {
        return repository.getNews(sources)
    }

    suspend fun getSearchNews(query: String): Flow<ResponseResult<MutableList<NewsEntity>>> {
        return repository.getSearchNews(query)
    }
}