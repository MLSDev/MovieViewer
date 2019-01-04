package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName
import com.shykun.volodymyr.movieviewer.data.entity.TV

data class GetTVResponse (
        @SerializedName("page") val page: Int,
        @SerializedName("results") val results: ArrayList<TV>,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int
)

enum class TVType(val path: String) {
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    ON_THE_AIR("on_the_air")
}