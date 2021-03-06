package com.tanjiajun.pagingdemo.data.remote.main

import com.tanjiajun.pagingdemo.data.model.ListData
import com.tanjiajun.pagingdemo.data.model.main.RepositoryData
import com.tanjiajun.pagingdemo.data.model.main.RepositoryMapper
import com.tanjiajun.pagingdemo.data.model.main.RepositoryResponseData
import com.tanjiajun.pagingdemo.utils.dateFormatForRepository
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDateTime

/**
 * Created by TanJiaJun on 7/26/21.
 */
class RepositoryRemoteDataSource(
    retrofit: Retrofit
) {

    private val service: Service = retrofit.create(Service::class.java)

    suspend fun fetchRepositories(
        languageName: String,
        fromDateTime: LocalDateTime,
        page: Int,
        pageSize: Int
    ): List<RepositoryData> =
        service
            .fetchRepositories(
                query = "language:${languageName} created:>${
                    fromDateTime.format(
                        dateFormatForRepository()
                    )
                }",
                sort = "stars",
                page = page,
                perPage = pageSize
            )
            .items
            ?.map { RepositoryMapper.toRepositoryData(it) }
            ?: emptyList()

    interface Service {

        @GET("search/repositories")
        suspend fun fetchRepositories(
            @Query("q") query: String,
            @Query("sort") sort: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int
        ): ListData<RepositoryResponseData>

    }

}