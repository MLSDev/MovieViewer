package com.shykun.volodymyr.movieviewer.data.entity

import com.google.gson.annotations.SerializedName


data class Season (
    @SerializedName("air_date") var airDate: String,
    @SerializedName("episode_count") var episodeCount: Int,
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("poster_path") var posterPath: String,
    @SerializedName("season_number") var seasonNumber: Int)