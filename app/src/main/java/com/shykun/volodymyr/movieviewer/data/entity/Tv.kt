package com.shykun.volodymyr.movieviewer.data.entity

import com.google.gson.annotations.SerializedName


data class Tv (
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("id") val id: Int,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("overview") val overview: String,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("origin_country") val originCountry: List<String>,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String
)

enum class TvType(val path: String) {
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    ON_THE_AIR("on_the_air"),
    SEARCHED("search"),
    RATED("rated"),
    WATCHLIST("watchlist"),
    FAVORITE("favorite"),
    DISCOVERED("discover")
}