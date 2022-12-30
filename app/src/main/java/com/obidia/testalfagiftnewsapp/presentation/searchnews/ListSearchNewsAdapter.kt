package com.obidia.testalfagiftnewsapp.presentation.searchnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.obidia.testalfagiftnewsapp.databinding.ItemArticlePreviewBinding
import com.obidia.testalfagiftnewsapp.domain.entity.NewsEntity

class ListSearchNewsAdapter(private val onClick: OnClick) :
    RecyclerView.Adapter<ListSearchNewsAdapter.ListViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<NewsEntity>() {
        override fun areItemsTheSame(
            oldItem: NewsEntity,
            newItem: NewsEntity
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()


        override fun areContentsTheSame(
            oldItem: NewsEntity,
            newItem: NewsEntity
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()


    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitData(value: MutableList<NewsEntity>?) = differ.submitList(value)

    class ListViewHolder(private var binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NewsEntity) {
            binding.entity = data
            binding.executePendingBindings()
        }
    }

    class OnClick(val click: (entity: NewsEntity?) -> Unit) {
        fun onClick(entity: NewsEntity?) = click(entity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ItemArticlePreviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = differ.currentList[position] as NewsEntity
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onClick.onClick(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}
