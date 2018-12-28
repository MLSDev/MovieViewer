package com.shykun.volodymyr.movieviewer.data

import com.google.gson.annotations.SerializedName

data class GetMovieResponse (
        @SerializedName("page") val page: Int,
        @SerializedName("results") val results: ArrayList<Movie>,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int
)