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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {
    private lateinit var binding: FragmentSearchNewsBinding
    private val viewModel: SearchNewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

            if (editable.toString().isNotEmpty()) {
                viewModel.searchNews(editable.toString())

            }
        }

        observe()
    }

    private fun observe() {
        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { state -> handleState(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: ListSearchBreakingNewsState) {
        with(binding) {
            when (state) {
                is ListSearchBreakingNewsState.Loading -> {
                    msvNews.viewState = MultiStateView.ViewState.LOADING
                }
                is ListSearchBreakingNewsState.Success -> {
                    msvNews.viewState = MultiStateView.ViewState.CONTENT
                }
                is ListSearchBreakingNewsState.Error -> {
                    msvNews.viewState = MultiStateView.ViewState.ERROR
                }
                else -> {}
            }
        }

    }

}