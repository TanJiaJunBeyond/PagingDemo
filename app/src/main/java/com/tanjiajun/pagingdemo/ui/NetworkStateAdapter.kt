package com.tanjiajun.pagingdemo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tanjiajun.pagingdemo.BR
import com.tanjiajun.pagingdemo.R
import com.tanjiajun.pagingdemo.databinding.ItemNetworkStateBinding

/**
 * Created by TanJiaJun on 8/1/21.
 */
class NetworkStateAdapter(
    private val handlers: Handlers
) : LoadStateAdapter<NetworkStateAdapter.NetworkStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateViewHolder =
        NetworkStateViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_network_state,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NetworkStateViewHolder, loadState: LoadState) {
        with(holder.binding) {
            setVariable(BR.data, loadState)
            setVariable(BR.handlers, this@NetworkStateAdapter.handlers)
            tvErrorMessage.text = (loadState as? LoadState.Error)?.error?.message
        }
    }

    class NetworkStateViewHolder(val binding: ItemNetworkStateBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface Handlers {

        /**
         * Click retry.
         * 点击重试。
         *
         * @param view
         */
        fun onRetryClick(view: View)

    }

}