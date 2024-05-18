package com.obidia.testalfagiftnewsapp.data.repository

import com.obidia.testalfagiftnewsapp.data.api.ApiClient
import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity
import com.obidia.testalfagiftnewsapp.domain.entity.SourcesEntity
import com.obidia.testalfagiftnewsapp.domain.repo.Repository
import com.obidia.testalfagiftnewsapp.utils.result.ResponseResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val apiClient: ApiClient
) : Repository {
    override suspend fun getSources(category: String): Flow<ResponseResult<MutableList<SourcesEntity>>> {
        return flow {
            try {
                emit(ResponseResult.Loading)
                delay(1000)
                val response = apiClient.getSources(category)
                if (response.isSuccessful) {
                    val data = mutableListOf<SourcesEntity>()
                    val body = response.body()?.sources
                    body?.forEach {
                        data.add(it.toSourceEntity())
                    }
                    emit(ResponseResult.Success(data))
                } else {
                    throw HttpException(response)
                }
            } catch (e: Throwable) {
                emit(ResponseResult.Error(e))
            }
        }
    }

    override suspend fun getNews(
        sources: String,
    ): Flow<ResponseResult<MutableList<NewsEntity>>> {
        return flow {
            try {
                emit(ResponseResult.Loading)
                delay(1000)
                val response = apiClient.getNews(sources)
                if (response.isSuccessful) {
                    val data = mutableListOf<NewsEntity>()
                    val body = response.body()?.articles
                    body?.forEach {
                        data.add(it?.toNewsEntity()!!)
                    }
                    emit(ResponseResult.Success(data))
                } else {
                    throw HttpException(response)
                }
            } catch (e: Throwable) {
                emit(ResponseResult.Error(e))
            }


        }
    }

    override suspend fun getAllSources(): Flow<ResponseResult<MutableList<SourcesEntity>>> {
        return flow {
            try {
                emit(ResponseResult.Loading)
                delay(1000)
                val response = apiClient.getAllSources()
                if (response.isSuccessful) {
                    val data = mutableListOf<SourcesEntity>()
                    val body = response.body()?.sources
                    body?.forEach {
                        data.add(it.toSourceEntity())
                    }
                    emit(ResponseResult.Success(data))
                } else {
                    throw HttpException(response)
                }
            } catch (e: Throwable) {
                emit(ResponseResult.Error(e))
            }


        }
    }

    override suspend fun getSearchNews(query: String): Flow<ResponseResult<MutableList<NewsEntity>>> {
        return flow {
            try {
                emit(ResponseResult.Loading)
                delay(1000)
                val response = apiClient.searchForNews(query)
                if (response.isSuccessful) {
                    val data = mutableListOf<NewsEntity>()
                    val body = response.body()?.articles
                    body?.forEach {
                        it?.toNewsEntity()?.let { dataResponse -> data.add(dataResponse) }
                    }
                    emit(ResponseResult.Success(data))
                } else {
                    throw HttpException(response)
                }
            } catch (e: Throwable) {
                emit(ResponseResult.Error(e))
            }

        }
    }
}