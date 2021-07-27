package com.tanjiajun.pagingdemo.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tanjiajun.pagingdemo.BR
import com.tanjiajun.pagingdemo.R
import com.tanjiajun.pagingdemo.data.model.main.RepositoryData
import com.tanjiajun.pagingdemo.databinding.ItemRepositoryBinding

/**
 * Created by TanJiaJun on 7/28/21.
 */
class RepositoryAdapter(
    private val repositories: List<RepositoryData>
) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

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
        holder.binding.setVariable(BR.data, repositories[position])
    }

    override fun getItemCount(): Int =
        repositories.size

    class RepositoryViewHolder(val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root)

}