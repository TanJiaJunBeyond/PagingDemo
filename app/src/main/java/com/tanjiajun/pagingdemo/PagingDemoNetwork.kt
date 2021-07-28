package com.tanjiajun.pagingdemo

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by TanJiaJun on 7/29/21.
 */
private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(PagingDemoConfiguration.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
    .readTimeout(PagingDemoConfiguration.READ_TIMEOUT, TimeUnit.MILLISECONDS)
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(String.format("%1\$s://%2\$s/", "https", PagingDemoConfiguration.HOST))
    .build()