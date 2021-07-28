package com.tanjiajun.pagingdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tanjiajun.pagingdemo.data.remote.main.RepositoryRemoteDataSource
import com.tanjiajun.pagingdemo.data.repository.GitHubRepository
import com.tanjiajun.pagingdemo.retrofit
import com.tanjiajun.pagingdemo.ui.main.viewmodel.MainViewModel

/**
 * Created by TanJiaJun on 7/29/21.
 */
class ViewModelFactory(
    private val repository: GitHubRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)
                else -> IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

}

val viewModelFactory =
    ViewModelFactory(GitHubRepository.getInstance(RepositoryRemoteDataSource(retrofit)))