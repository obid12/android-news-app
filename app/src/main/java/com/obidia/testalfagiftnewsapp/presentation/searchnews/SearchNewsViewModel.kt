package com.obidia.testalfagiftnewsapp.presentation.searchnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity
import com.obidia.testalfagiftnewsapp.domain.usecase.UseCase
import com.obidia.testalfagiftnewsapp.utils.result.ResponseResult
import com.obidia.testalfagiftnewsapp.utils.result.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {
    private val _data = MutableStateFlow<ResponseResult<MutableList<NewsEntity>>?>(null)
    val data get() = _data
    val listNews = MutableStateFlow<MutableList<NewsEntity>>(mutableListOf())

    fun searchNews(searchQuery: String) {
        viewModelScope.launch {
            useCase.getSearchNews(searchQuery).catch {
                _data.value = ResponseResult.Error(it)
            }.collect {
                it.success { list ->
                    listNews.value = list
                }
                _data.value = it
            }
        }
    }
}