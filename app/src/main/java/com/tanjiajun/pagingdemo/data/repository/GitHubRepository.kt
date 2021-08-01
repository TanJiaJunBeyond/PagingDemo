package com.tanjiajun.pagingdemo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tanjiajun.pagingdemo.data.model.main.RepositoryData
import com.tanjiajun.pagingdemo.data.remote.main.PageKeyedRepositoryPagingSource
import com.tanjiajun.pagingdemo.data.remote.main.RepositoryRemoteDataSource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * Created by TanJiaJun on 7/27/21.
 */
class GitHubRepository private constructor(
    private val remoteDataSource: RepositoryRemoteDataSource
) {

    fun getRepositories(languageName: String, pageSize: Int): Flow<PagingData<RepositoryData>> =
        Pager(
            PagingConfig(
                initialLoadSize = pageSize,
                pageSize = pageSize,
                enablePlaceholders = false
            )
        ) {
            PageKeyedRepositoryPagingSource(
                remoteDataSource = remoteDataSource,
                languageName = languageName,
                fromDateTime = LocalDateTime.now().minusMonths(1)
            )
        }.flow

    companion object {
        @Volatile
        private var instance: GitHubRepository? = null

        @JvmStatic
        fun getInstance(remoteDataSource: RepositoryRemoteDataSource): GitHubRepository =
            instance ?: synchronized(this) {
                instance ?: GitHubRepository(remoteDataSource)
            }
    }

}