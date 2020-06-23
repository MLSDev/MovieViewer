package com.shykun.volodymyr.movieviewer.data.entity

import com.google.gson.annotations.SerializedName


data class Role(
    @SerializedName("id") var id: Int,
    @SerializedName("original_language") var originalLanguage: String,
    @SerializedName("episode_count") var episodeCount: Int,
    @SerializedName("overview") var overview: String,
    @SerializedName("origin_country") var originCountry: List<String>,
    @SerializedName("original_name") var originalName: String,
    @SerializedName("genre_ids") var genreIds: List<Int>,
    @SerializedName("name") var name: String,
    @SerializedName("media_type") var mediaType: String,
    @SerializedName("poster_path") var posterPath: String,
    @SerializedName("first_air_date") var firstAirDate: String,
    @SerializedName("vote_average") var voteAverage: Double,
    @SerializedName("vote_count") var voteCount: Int,
    @SerializedName("character") var character: String,
    @SerializedName("backdrop_path") var backdropPath: String,
    @SerializedName("popularity") var popularity: Double,
    @SerializedName("credit_id") var creditId: String)