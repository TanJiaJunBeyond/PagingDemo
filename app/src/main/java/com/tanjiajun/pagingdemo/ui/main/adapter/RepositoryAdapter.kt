package com.tanjiajun.pagingdemo.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tanjiajun.pagingdemo.BR
import com.tanjiajun.pagingdemo.R
import com.tanjiajun.pagingdemo.data.model.main.RepositoryData
import com.tanjiajun.pagingdemo.databinding.ItemRepositoryBinding

/**
 * Created by TanJiaJun on 7/28/21.
 */
class RepositoryAdapter :
    PagingDataAdapter<RepositoryData, RepositoryAdapter.RepositoryViewHolder>(REPOSITORY_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
        RepositoryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_repository,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.binding.setVariable(BR.data, getItem(position))
    }

    class RepositoryViewHolder(val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val REPOSITORY_COMPARATOR = object : DiffUtil.ItemCallback<RepositoryData>() {
            override fun areItemsTheSame(
                oldItem: RepositoryData,
                newItem: RepositoryData
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: RepositoryData,
                newItem: RepositoryData
            ): Boolean =
                oldItem == newItem

        }
    }

}