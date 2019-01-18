package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName
import com.shykun.volodymyr.movieviewer.data.entity.Review


data class GetMovieReviewsResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("page") var page: Int,
        @SerializedName("results") var results: List<Review>,
        @SerializedName("total_pages") var totalPages: Int,
        @SerializedName("total_results") var totalResults: Int)