package com.obidia.testalfagiftnewsapp.presentation.categoryandsources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kennyc.view.MultiStateView
import com.obidia.testalfagiftnewsapp.R
import com.obidia.testalfagiftnewsapp.data.model.CategoryResponse
import com.obidia.testalfagiftnewsapp.databinding.FragmentCategoryBinding
import com.obidia.testalfagiftnewsapp.domain.entity.SourcesEntity
import com.obidia.testalfagiftnewsapp.utils.bindRecyclerViewListCatgory
import com.obidia.testalfagiftnewsapp.utils.result.ResponseResult
import com.obidia.testalfagiftnewsapp.utils.result.error
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private val list = mutableListOf<CategoryResponse>()
    private val viewModel by viewModels<ListSourcesViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllSources()
        observeSources()

        with(binding) {
            rvCategory.adapter = CategoryAdapter(
                CategoryAdapter.OnClick {
                    viewModel?.sources(it?.categoryName!!)
                    observeSources()
                }, requireContext()
            )

            list.addAll(listUsers)
            bindRecyclerViewListCatgory(rvCategory, list)

            binding.rvSources.adapter = ListSourcesAdapter(
                ListSourcesAdapter.OnClick {
                    if (it == null) return@OnClick
                    findNavController().navigate(
                        CategoryFragmentDirections.actionCategoryFragmentToBreakingNewsFragment(
                            it.idSource
                        )
                    )
                }
            )
        }
    }

    private fun observeSources() {
        lifecycleScope.launch {
            viewModel.data.flowWithLifecycle(lifecycle).collect { state ->
                handleState(state)
            }
        }
    }

    private fun handleState(state: ResponseResult<MutableList<SourcesEntity>>?) {
        with(binding) {
            when (state) {
                is ResponseResult.Loading -> {
                    msvSources.viewState = MultiStateView.ViewState.LOADING
                }

                is ResponseResult.Success -> {
                    msvSources.viewState = MultiStateView.ViewState.CONTENT
                }

                is ResponseResult.Error -> {
                    state.error {
                        if (it is IOException) {
                            msvSources.viewState = MultiStateView.ViewState.ERROR
                            return
                        }

                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                }

                else -> {}
            }
        }
    }

    private val listUsers: MutableList<CategoryResponse>
        get() {
            val dataCategory = resources.getStringArray(R.array.category)

            val listUser = mutableListOf<CategoryResponse>()
            for (i in dataCategory.indices) {
                val category = CategoryResponse(
                    dataCategory[i],
                )
                listUser.add(category)
            }
            return listUser
        }
}