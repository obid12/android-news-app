package com.obidia.testalfagiftnewsapp.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import com.obidia.testalfagiftnewsapp.R
import com.obidia.testalfagiftnewsapp.data.model.CategoryResponse
import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity
import com.obidia.testalfagiftnewsapp.domain.entity.SourcesEntity
import com.obidia.testalfagiftnewsapp.presentation.categoryandsources.CategoryAdapter
import com.obidia.testalfagiftnewsapp.presentation.categoryandsources.ListSourcesAdapter
import com.obidia.testalfagiftnewsapp.presentation.news.BreakingNewsAdapter
import com.obidia.testalfagiftnewsapp.presentation.searchnews.ListSearchNewsAdapter

@BindingAdapter("listCategpry")
fun bindRecyclerViewListCatgory(
    recycler: RecyclerView,
    data: MutableList<CategoryResponse>?
) {
    val adapter = recycler.adapter as CategoryAdapter
    adapter.submitData(data)
}

@BindingAdapter("listSources")
fun bindRecyclerViewListSources(
    recycler: RecyclerView,
    data: MutableList<SourcesEntity>?
) {
    val adapter = recycler.adapter as ListSourcesAdapter
    adapter.submitData(data)
}

@BindingAdapter("listNews")
fun bindRecyclerViewListNews(
    recycler: RecyclerView,
    data: MutableList<NewsEntity>?
) {
    val adapter = recycler.adapter as BreakingNewsAdapter
    adapter.submitData(data)
}

@BindingAdapter("listSearchNews")
fun bindRecyclerViewListSearchNews(
    recycler: RecyclerView,
    data: MutableList<NewsEntity>?
) {
    val adapter = recycler.adapter as ListSearchNewsAdapter
    adapter.submitData(data)
}

@BindingAdapter("imageNewsURL")
fun getImageNewsUrl(iv: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imageUrl = ImageRequest.Builder(iv.context)
            .data("${it.toUri()}")
            .allowHardware(false)
            .build()
        iv.load("${imageUrl.data}") {
            placeholder(R.drawable.loading_animation)
            this.error(R.drawable.ic_broken_image)
        }
    }
}