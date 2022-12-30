package com.obidia.testalfagiftnewsapp.presentation.categoryandsources

import android.annotation.SuppressLint
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
import com.obidia.testalfagiftnewsapp.data.model.response.CategoryResponse
import com.obidia.testalfagiftnewsapp.databinding.FragmentCategoryBinding
import com.obidia.testalfagiftnewsapp.utils.bindRecyclerViewListCatgory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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

        with(binding) {

            rvCategory.adapter = CategoryAdapter(
                CategoryAdapter.OnClick {
                    viewModel?.sources(it?.categoryName!!)
                }, requireContext()
            )

            list.addAll(listUsers)
            bindRecyclerViewListCatgory(rvCategory, list)

            binding.rvSources.adapter = ListSourcesAdapter(
                ListSourcesAdapter.OnClick {
                    if (it != null) {
                        findNavController().navigate(
                            CategoryFragmentDirections.actionCategoryFragmentToBreakingNewsFragment(
                                it.idSource
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
        viewModel.getAllSources()
        observeSources()
    }

    private fun observeSources() {
        viewModel.state.flowWithLifecycle(lifecycle).onEach { state ->
            handleState(state)
        }.launchIn(lifecycleScope)
    }

    private fun handleState(state: ListSourceState) {
        with(binding) {
            when (state) {
                is ListSourceState.Loading -> {
                    msvSources.viewState = MultiStateView.ViewState.LOADING
                }
                is ListSourceState.Success -> {
                    msvSources.viewState = MultiStateView.ViewState.CONTENT
                }
                is ListSourceState.Error -> {
                    if (state.error == "Network Failure") {
                        msvSources.viewState = MultiStateView.ViewState.ERROR
                    } else {
                        Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                    }
                }
                else -> {}
            }
        }


    }

    private val listUsers: MutableList<CategoryResponse>
        @SuppressLint("Recycle")
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