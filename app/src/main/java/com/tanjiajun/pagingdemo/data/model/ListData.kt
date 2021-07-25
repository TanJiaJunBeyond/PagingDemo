package com.tanjiajun.pagingdemo.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by TanJiaJun on 7/26/21.
 */
data class ListData<T>(
    @SerializedName("total_count") val totalCount: Int? = null,
    @SerializedName("incomplete_results") val incompleteResults: Boolean? = null,
    var items: List<T>? = null
)