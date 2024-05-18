package com.obidia.testalfagiftnewsapp.presentation.searchnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.kennyc.view.MultiStateView
import com.obidia.testalfagiftnewsapp.databinding.FragmentSearchNewsBinding
import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity
import com.obidia.testalfagiftnewsapp.utils.result.ResponseResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {
    private lateinit var binding: FragmentSearchNewsBinding
    private val viewModel: SearchNewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvSearchNews.adapter = ListSearchNewsAdapter(
            ListSearchNewsAdapter.OnClick {

            }
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.addTextChangedListener { editable ->
            if (editable.toString().isEmpty()) return@addTextChangedListener
            viewModel.searchNews(editable.toString())
        }

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.data.flowWithLifecycle(lifecycle).collect { state -> handleState(state) }
        }
    }

    private fun handleState(state: ResponseResult<MutableList<NewsEntity>>?) {
        with(binding) {
            when (state) {
                is ResponseResult.Loading -> {
                    msvNews.viewState = MultiStateView.ViewState.LOADING
                }

                is ResponseResult.Success -> {
                    msvNews.viewState = MultiStateView.ViewState.CONTENT
                }

                is ResponseResult.Error -> {
                    msvNews.viewState = MultiStateView.ViewState.ERROR
                }

                else -> {}
            }
        }
    }
}