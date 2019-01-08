package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName
import com.shykun.volodymyr.movieviewer.data.entity.Person

data class GetPeopleResponse(
        @SerializedName("page") val page: Int,
        @SerializedName("results") val results: ArrayList<Person>,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int
)