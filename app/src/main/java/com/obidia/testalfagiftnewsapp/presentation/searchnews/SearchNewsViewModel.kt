package com.obidia.testalfagiftnewsapp.presentation.searchnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity
import com.obidia.testalfagiftnewsapp.domain.usecase.UseCase
import com.obidia.testalfagiftnewsapp.utils.result.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {
    private val _data = MutableStateFlow<MutableList<NewsEntity>>(mutableListOf())
    val data get() = _data

    private val _state =
        MutableStateFlow<ListSearchBreakingNewsState>(ListSearchBreakingNewsState.Init)
    val state get() = _state

    private fun loading() {
        _state.value = ListSearchBreakingNewsState.Loading()
    }

    private fun success(dataEntity: MutableList<NewsEntity>) {
        _state.value = ListSearchBreakingNewsState.Success(dataEntity)
        _data.value = dataEntity
    }

    private fun errorData() {
        _state.value = ListSearchBreakingNewsState.Error("Ada masalah")

    }

    fun searchNews(searchQuery: String) {
        viewModelScope.launch {
            useCase.getSercahNews(searchQuery)
                .onStart {
                    loading()

                }.catch {
                    errorData()
                }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> result.data?.let { success(it) }
                        is Resource.Error -> {
                            errorData()
                        }
                        else -> {}
                    }
                }
        }
    }
}

sealed class ListSearchBreakingNewsState {
    object Init : ListSearchBreakingNewsState()
    data class Loading(val loading: Boolean = true) : ListSearchBreakingNewsState()
    data class Success(val dataEntity: MutableList<NewsEntity>) : ListSearchBreakingNewsState()
    data class Error(val error: String) : ListSearchBreakingNewsState()
}