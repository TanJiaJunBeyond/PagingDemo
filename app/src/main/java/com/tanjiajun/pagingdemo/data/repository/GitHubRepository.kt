package com.tanjiajun.pagingdemo.data.repository

import com.tanjiajun.pagingdemo.data.model.main.RepositoryData
import com.tanjiajun.pagingdemo.data.remote.main.RepositoryRemoteDataSource
import java.time.LocalDateTime

/**
 * Created by TanJiaJun on 7/27/21.
 */
class GitHubRepository private constructor(
    private val remoteDataSource: RepositoryRemoteDataSource
) {

    suspend fun getRepositories(languageName: String): List<RepositoryData> =
        remoteDataSource.fetchRepositories(
            languageName = languageName,
            fromDateTime = LocalDateTime.now().minusMonths(1)
        )

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