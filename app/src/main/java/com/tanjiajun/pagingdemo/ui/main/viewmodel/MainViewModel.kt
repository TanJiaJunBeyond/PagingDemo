package com.tanjiajun.pagingdemo.ui.main.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanjiajun.pagingdemo.data.model.main.RepositoryData
import com.tanjiajun.pagingdemo.data.repository.GitHubRepository
import com.tanjiajun.pagingdemo.utils.yes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    // 是否显示仓库页面
    private val _isShowRepositoryView = MutableLiveData<Boolean>()
    val isShowRepositoryView: LiveData<Boolean> = _isShowRepositoryView

    private val _repositories = MutableLiveData<List<RepositoryData>>()
    val repositories: LiveData<List<RepositoryData>> = _repositories

    fun getRepositories(languageName: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val repositories: List<RepositoryData> =
                        repository.getRepositories(languageName)
                    withContext(Dispatchers.Main) {
                        repositories
                            .isNotEmpty()
                            .yes {
                                _repositories.value = repositories
                                _isShowRepositoryView.value = true
                            }
                    }
                }
            } catch (throwable: Throwable) {
                _isShowErrorView.value = true
            } finally {
                _isShowLoadingView.value = true
            }
        }
    }

    interface Handlers {

        /**
         * 点击重试
         */
        fun onRetryClick(view: View)

    }

}