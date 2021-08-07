package com.tanjiajun.pagingdemo.ui.main.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tanjiajun.pagingdemo.data.model.main.RepositoryData
import com.tanjiajun.pagingdemo.data.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by TanJiaJun on 7/26/21.
 */
class MainViewModel(
    private val repository: GitHubRepository
) : ViewModel() {

    // 是否显示加载中页面
    private val _isShowLoadingView = MutableLiveData<Boolean>()
    val isShowLoadingView: LiveData<Boolean> = _isShowLoadingView

    // 是否显示失败页面
    private val _isShowErrorView = MutableLiveData<Boolean>()
    val isShowErrorView: LiveData<Boolean> = _isShowErrorView

    fun getRepositories(languageName: String): Flow<PagingData<RepositoryData>> =
        repository.getRepositories(
            languageName = languageName,
            pageSize = 20
        ).cachedIn(viewModelScope)

    interface Handlers {

        /**
         * 点击重试
         */
        fun onRetryClick(view: View)

    }

}