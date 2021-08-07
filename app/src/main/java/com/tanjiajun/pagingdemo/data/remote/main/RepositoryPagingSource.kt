package com.tanjiajun.pagingdemo.data.remote.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tanjiajun.pagingdemo.data.model.main.RepositoryData
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDateTime

/**
 * Created by TanJiaJun on 7/31/21.
 */
class RepositoryPagingSource(
    private val remoteDataSource: RepositoryRemoteDataSource,
    private val languageName: String,
    private val fromDateTime: LocalDateTime
) : PagingSource<Int, RepositoryData>() {

    override fun getRefreshKey(state: PagingState<Int, RepositoryData>): Int? =
        state.anchorPosition
            ?.let {
                state.closestPageToPosition(it)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
            }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryData> =
        try {
            val position: Int = params.key ?: GITHUB_STARTING_PAGE_INDEX
            val data: List<RepositoryData> =
                remoteDataSource.fetchRepositories(
                    languageName = languageName,
                    fromDateTime = fromDateTime,
                    page = position,
                    pageSize = params.loadSize
                )
            LoadResult.Page(
                data = data,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (data.isNullOrEmpty()) null else position + 1
            )
        } catch (ioException: IOException) {
            LoadResult.Error(ioException)
        } catch (httpException: HttpException) {
            LoadResult.Error(httpException)
        }

    private companion object {
        const val GITHUB_STARTING_PAGE_INDEX = 1
    }

}