package com.obidia.testalfagiftnewsapp.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kennyc.view.MultiStateView
import com.obidia.testalfagiftnewsapp.R
import com.obidia.testalfagiftnewsapp.databinding.FragmentBreakingNewsBinding
import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity
import com.obidia.testalfagiftnewsapp.utils.result.ResponseResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {
    private lateinit var binding: FragmentBreakingNewsBinding
    private val viewModel: BreakingNewsViewModel by viewModels()
    private val arg by navArgs<BreakingNewsFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setToolbar()
        with(binding) {
            rvNews.adapter = BreakingNewsAdapter(
                BreakingNewsAdapter.OnClick {
                    if (it == null) return@OnClick
                    findNavController().navigate(
                        BreakingNewsFragmentDirections.actionBreakingNewsFragmentToDetailArticleFragment(
                            it.url
                        )
                    )
                }
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBreakingNews(arg.sources)
        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.data.flowWithLifecycle(lifecycle).collect { state ->
                handleState(state)
            }
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

    private fun setToolbar() {
        binding.toolbar.title = "List News"
        binding.toolbar.setNavigationIcon(R.drawable.ic_search)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(BreakingNewsFragmentDirections.actionBreakingNewsFragmentToSearchNewsFragment())
        }

    }
}