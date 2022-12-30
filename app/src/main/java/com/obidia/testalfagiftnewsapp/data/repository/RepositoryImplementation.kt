package com.obidia.testalfagiftnewsapp.data.repository

import com.obidia.testalfagiftnewsapp.data.api.ApiClient
import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity
import com.obidia.testalfagiftnewsapp.domain.entity.SourcesEntity
import com.obidia.testalfagiftnewsapp.domain.repo.Repository
import com.obidia.testalfagiftnewsapp.utils.result.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val apiClient: ApiClient
) : Repository {
    override suspend fun getSourcs(category: String): Flow<Resource<MutableList<SourcesEntity>>> {
        return flow {
            try {

                val response = apiClient.getSources(category)
                delay(1000)
                if (response.isSuccessful) {
                    val data = mutableListOf<SourcesEntity>()
                    val body = response.body()?.sources
                    body?.forEach {
                        data.add(it.toSourceEntity())
                    }
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Error(response.message()))
                }

            } catch (e: IOException) {

                emit(Resource.Error("Network Failure"))

            }


        }
    }

    override suspend fun getNews(
        sources: String,
    ): Flow<Resource<MutableList<NewsEntity>>> {
        return flow {
            try {
                val response = apiClient.getNews(sources)
                delay(1000)
                if (response.isSuccessful) {
                    val data = mutableListOf<NewsEntity>()
                    val body = response.body()?.articles
                    body?.forEach {
                        data.add(it?.toNewsEntity()!!)
                    }
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Error(response.message()))
                }
            } catch (e: IOException) {

                emit(Resource.Error("Network Failure"))

            }


        }
    }

    override suspend fun getAllSources(): Flow<Resource<MutableList<SourcesEntity>>> {
        return flow {
            try {
                val response = apiClient.getAllSources()
                delay(1000)
                if (response.isSuccessful) {
                    val data = mutableListOf<SourcesEntity>()
                    val body = response.body()?.sources
                    body?.forEach {
                        data.add(it.toSourceEntity())
                    }
                    emit(Resource.Success(data))
                } else {

                    emit(Resource.Error(response.message()))

                }
            } catch (e: IOException) {

                emit(Resource.Error("Network Failure"))

            }


        }
    }

    override suspend fun getSearchNews(query: String): Flow<Resource<MutableList<NewsEntity>>> {
        return flow {
            try {

                val response = apiClient.searchForNews(query)
                delay(1000)
                if (response.isSuccessful) {
                    val data = mutableListOf<NewsEntity>()
                    val body = response.body()?.articles
                    body?.forEach {
                        it?.toNewsEntity()?.let { dataResponse -> data.add(dataResponse) }
                    }
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Error(response.message()))
                }
            } catch (e: IOException) {

                emit(Resource.Error("Network Failure"))

            }

        }
    }


}