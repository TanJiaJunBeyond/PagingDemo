package com.tanjiajun.pagingdemo.ui.main.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanjiajun.pagingdemo.R
import com.tanjiajun.pagingdemo.databinding.ActivityMainBinding
import com.tanjiajun.pagingdemo.databinding.LayoutErrorBinding
import com.tanjiajun.pagingdemo.ui.NetworkStateAdapter
import com.tanjiajun.pagingdemo.ui.main.adapter.RepositoryAdapter
import com.tanjiajun.pagingdemo.ui.main.viewmodel.MainViewModel
import com.tanjiajun.pagingdemo.ui.viewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

/**
 * Created by TanJiaJun on 7/25/21.
 */
class MainActivity : AppCompatActivity(), MainViewModel.Handlers, NetworkStateAdapter.Handlers {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: RepositoryAdapter

    private var progressBar: ProgressBar? = null
    private var errorView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViewModel()
        initUI()
        initObservers()
    }

    override fun onRetryClick(view: View) {
        adapter.retry()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private fun initUI() {
        with(binding) {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
        initErrorView()
        initRecyclerView()
        initSwipeToRefresh()
    }

    private fun initRecyclerView() {
        adapter = RepositoryAdapter()
        with(binding.rvRepository) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter.withLoadStateHeaderAndFooter(
                header = NetworkStateAdapter(this@MainActivity),
                footer = NetworkStateAdapter(this@MainActivity)
            )
        }
        adapter.addLoadStateListener {
            val isShowLoadingView = it.refresh is LoadState.Loading && adapter.itemCount == 0
            handleLoadingView(isShowLoadingView)
        }
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                binding.swipeRefreshLayout.isRefreshing = it.mediator?.refresh is LoadState.Loading
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.getRepositories("Kotlin").collectLatest {
                adapter.submitData(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collectLatest { binding.rvRepository.scrollToPosition(0) }
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }
    }

    private fun initErrorView() {
        binding.vsError.setOnInflateListener { _, inflated ->
            DataBindingUtil.bind<LayoutErrorBinding>(inflated)?.run {
                lifecycleOwner = this@MainActivity
                viewModel = this@MainActivity.viewModel
                handlers = this@MainActivity
            }
        }
    }

    private fun initObservers() {
        viewModel.isShowLoadingView.observe(this, { handleLoadingView(it) })
        viewModel.isShowErrorView.observe(this, { handleErrorView(it) })
    }

    private fun handleLoadingView(isShowLoadingView: Boolean) {
        if (isShowLoadingView) {
            findViewById<FrameLayout>(android.R.id.content).addView(
                ProgressBar(this)
                    .apply {
                        layoutParams = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                        ).also { it.gravity = Gravity.CENTER }
                    }
                    .also { progressBar = it }
            )
        } else {
            progressBar?.let { findViewById<FrameLayout>(android.R.id.content).removeView(it) }
        }
    }

    private fun handleErrorView(isShowErrorView: Boolean) {
        if (isShowErrorView) {
            errorView
                ?.run { visibility = View.VISIBLE }
                ?: binding.vsError.viewStub?.inflate()?.also { errorView = it }
        } else {
            errorView?.visibility = View.GONE
        }
    }

}