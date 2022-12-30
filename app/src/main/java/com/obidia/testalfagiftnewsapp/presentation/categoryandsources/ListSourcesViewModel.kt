package com.obidia.testalfagiftnewsapp.presentation.categoryandsources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obidia.testalfagiftnewsapp.domain.entity.SourcesEntity
import com.obidia.testalfagiftnewsapp.domain.usecase.UseCase
import com.obidia.testalfagiftnewsapp.utils.result.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListSourcesViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {
    private val _state = MutableStateFlow<ListSourceState>(ListSourceState.Init)
    val state get() = _state

    private val _data = MutableStateFlow<MutableList<SourcesEntity>>(mutableListOf())
    val data: StateFlow<MutableList<SourcesEntity>> get() = _data


    private fun loading() {
        _state.value = ListSourceState.Loading()
    }

    private fun success(dataEntity: MutableList<SourcesEntity>) {
        _state.value = ListSourceState.Success(dataEntity)
    }

    private fun errorData(data: String) {
        _state.value = ListSourceState.Error(data)

    }

    fun getAllSources() {
        viewModelScope.launch {
            useCase.getAllSources()
                .onStart {
                    loading()
                }.catch {

                }.collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { success(it) }
                            _data.value = result.data!!
                        }
                        is Resource.Error -> {
                        }
                        else -> {}
                    }
                }
        }
    }


    fun sources(category: String) {
        viewModelScope.launch {
            useCase.getSources(category)
                .onStart {
                    loading()

                }.catch {
                    errorData("Network Failure")
                }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { success(it) }
                            _data.value = result.data!!
                        }
                        is Resource.Error -> {
                            result.message?.let { errorData(it) }
                        }
                        else -> {}
                    }
                }
        }
    }
}

sealed class ListSourceState {
    object Init : ListSourceState()
    data class Loading(val loading: Boolean = true) : ListSourceState()
    data class Success(val dataEntity: MutableList<SourcesEntity>) : ListSourceState()
    data class Error(val error: String) : ListSourceState()
}