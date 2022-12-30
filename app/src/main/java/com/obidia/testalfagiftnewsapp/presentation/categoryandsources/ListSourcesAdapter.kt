package com.obidia.testalfagiftnewsapp.presentation.categoryandsources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.obidia.testalfagiftnewsapp.databinding.ItemSourcesBinding
import com.obidia.testalfagiftnewsapp.domain.entity.SourcesEntity

class ListSourcesAdapter(private val onClick: OnClick) :
    RecyclerView.Adapter<ListSourcesAdapter.ListViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<SourcesEntity>() {
        override fun areItemsTheSame(
            oldItem: SourcesEntity,
            newItem: SourcesEntity
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()


        override fun areContentsTheSame(
            oldItem: SourcesEntity,
            newItem: SourcesEntity
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()


    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitData(value: MutableList<SourcesEntity>?) = differ.submitList(value)

    class ListViewHolder(private var binding: ItemSourcesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SourcesEntity) {
            binding.entity = data
            binding.executePendingBindings()
        }
    }

    class OnClick(val click: (entity: SourcesEntity?) -> Unit) {
        fun onClick(entity: SourcesEntity?) = click(entity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ItemSourcesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = differ.currentList[position] as SourcesEntity
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onClick.onClick(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}