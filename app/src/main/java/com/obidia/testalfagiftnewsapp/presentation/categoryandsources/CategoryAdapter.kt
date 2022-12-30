package com.obidia.testalfagiftnewsapp.presentation.categoryandsources

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.obidia.testalfagiftnewsapp.R
import com.obidia.testalfagiftnewsapp.data.model.response.CategoryResponse
import com.obidia.testalfagiftnewsapp.databinding.ItemCategoryBinding

class CategoryAdapter(val onClick: OnClick, private var context: Context) :
    RecyclerView.Adapter<CategoryAdapter.ListViewHolder>() {

    var rowIndex = -1

    private val diffCallback = object : DiffUtil.ItemCallback<CategoryResponse>() {
        override fun areItemsTheSame(
            oldItem: CategoryResponse,
            newItem: CategoryResponse
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()


        override fun areContentsTheSame(
            oldItem: CategoryResponse,
            newItem: CategoryResponse
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()


    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitData(value: MutableList<CategoryResponse>?) = differ.submitList(value)

    class ListViewHolder(var binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CategoryResponse) {
            binding.entity = data
            binding.executePendingBindings()
        }
    }

    class OnClick(val click: (entity: CategoryResponse?) -> Unit) {
        fun onClick(entity: CategoryResponse?) = click(entity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = differ.currentList[position] as CategoryResponse
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onClick.onClick(data)
            rowIndex = position
            notifyDataSetChanged()
        }

        if (rowIndex == position){
            holder.itemView.setBackgroundColor(context.resources.getColor(R.color.purple_200))
            holder.binding.tvCategoryTitle.setTextColor(context.resources.getColor(R.color.white))
        }else {
            holder.itemView.setBackgroundColor(context.resources.getColor(R.color.white))
            holder.binding.tvCategoryTitle.setTextColor(context.resources.getColor(R.color.black))
        }

    }

    override fun getItemCount(): Int = differ.currentList.size
}