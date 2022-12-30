package com.obidia.testalfagiftnewsapp.domain.usecase

import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity
import com.obidia.testalfagiftnewsapp.domain.entity.SourcesEntity
import com.obidia.testalfagiftnewsapp.domain.repo.Repository
import com.obidia.testalfagiftnewsapp.utils.result.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun getSources(category: String):
            Flow<Resource<MutableList<SourcesEntity>>> {
        return repository.getSourcs(category)
    }

    suspend fun getAllSources(): Flow<Resource<MutableList<SourcesEntity>>> {
        return repository.getAllSources()
    }

    suspend fun getAllNews(sources: String): Flow<Resource<MutableList<NewsEntity>>> {
        return repository.getNews(sources)
    }

    suspend fun getSercahNews(query: String):Flow<Resource<MutableList<NewsEntity>>>{
        return repository.getSearchNews(query)
    }
}