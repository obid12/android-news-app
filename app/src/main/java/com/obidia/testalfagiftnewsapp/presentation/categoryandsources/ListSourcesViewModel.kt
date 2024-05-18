package com.obidia.testalfagiftnewsapp.presentation.categoryandsources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obidia.testalfagiftnewsapp.domain.entity.SourcesEntity
import com.obidia.testalfagiftnewsapp.domain.usecase.UseCase
import com.obidia.testalfagiftnewsapp.utils.result.ResponseResult
import com.obidia.testalfagiftnewsapp.utils.result.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListSourcesViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _data = MutableStateFlow<ResponseResult<MutableList<SourcesEntity>>?>(null)
    val data get() = _data
    val listSources = MutableStateFlow<MutableList<SourcesEntity>>(mutableListOf())

    fun getAllSources() {
        viewModelScope.launch {
            useCase.getAllSources()
                .catch { _data.value = ResponseResult.Error(it) }
                .collect {
                    _data.value = it
                    it.success { list ->
                        listSources.value = list
                    }
                }
        }
    }

    fun sources(category: String) {
        viewModelScope.launch {
            useCase.getSources(category)
                .catch { _data.value = ResponseResult.Error(it) }
                .collect {
                    it.success { list ->
                        listSources.value = list
                    }
                    _data.value = it
                }
        }
    }
}