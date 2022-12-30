package com.obidia.testalfagiftnewsapp.presentation.news

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
class BreakingNewsViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {
    private val _data = MutableStateFlow<MutableList<NewsEntity>>(mutableListOf())
    val data get() = _data

    private val _state = MutableStateFlow<ListBreakingNewsState>(ListBreakingNewsState.Init)
    val state get() = _state

    private fun loading() {
        _state.value = ListBreakingNewsState.Loading()
    }

    private fun success(dataEntity: MutableList<NewsEntity>) {
        _state.value = ListBreakingNewsState.Success(dataEntity)
    }

    private fun errorData() {
        _state.value = ListBreakingNewsState.Error("Ada masalah")

    }

    fun getBreakingNews(sources: String) {
        viewModelScope.launch {
            useCase.getAllNews(sources)
                .onStart {
                    loading()
                }.catch {
                    errorData()
                }.collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { success(it) }
                            _data.value = result.data!!
                        }
                        is Resource.Error -> {
                            errorData()
                        }
                        else -> {}
                    }
                }
        }
    }
}

sealed class ListBreakingNewsState {
    object Init : ListBreakingNewsState()
    data class Loading(val loading: Boolean = true) : ListBreakingNewsState()
    data class Success(val dataEntity: MutableList<NewsEntity>) : ListBreakingNewsState()
    data class Error(val error: String) : ListBreakingNewsState()
}