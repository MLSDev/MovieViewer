package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName
import com.shykun.volodymyr.movieviewer.data.entity.TV

data class GetTVResponse (
        @SerializedName("page") val page: Int,
        @SerializedName("results") val results: ArrayList<TV>,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int
)