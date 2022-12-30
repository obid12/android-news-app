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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {
    private lateinit var binding: FragmentBreakingNewsBinding
    private val viewModel: BreakingNewsViewModel by viewModels()
    private val arg by navArgs<BreakingNewsFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setToolbar()
        with(binding) {
            rvNews.adapter = BreakingNewsAdapter(
                BreakingNewsAdapter.OnClick {
                    if (it != null) {
                        findNavController().navigate(
                            BreakingNewsFragmentDirections.actionBreakingNewsFragmentToDetailArticleFragment(
                                it.url
                            )
                        )
                    }
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
        viewModel.state.flowWithLifecycle(lifecycle).onEach { state ->
            handleState(state)
        }.launchIn(lifecycleScope)
    }

    private fun handleState(state: ListBreakingNewsState) {
        with(binding) {
            when (state) {
                is ListBreakingNewsState.Loading -> {
                    msvNews.viewState = MultiStateView.ViewState.LOADING
                }

                is ListBreakingNewsState.Success -> {
                    msvNews.viewState = MultiStateView.ViewState.CONTENT
                }
                is ListBreakingNewsState.Error -> {
                    msvNews.viewState = MultiStateView.ViewState.ERROR
                }
                else -> {}
            }
        }

    }

    private fun setToolbar() {
        binding.toolbar.title = "Detail User"
        binding.toolbar.setNavigationIcon(R.drawable.ic_search)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(BreakingNewsFragmentDirections.actionBreakingNewsFragmentToSearchNewsFragment())
        }

    }
}