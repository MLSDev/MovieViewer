package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName
import com.shykun.volodymyr.movieviewer.data.entity.Company
import com.shykun.volodymyr.movieviewer.data.entity.Country
import com.shykun.volodymyr.movieviewer.data.entity.Genre
import com.shykun.volodymyr.movieviewer.data.entity.Language


data class GetMovieDetailsResponse(
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("backdrop_path") val backdropPath: String,
        @SerializedName("belongs_to_collection") val belongsToCollection: Any,
        @SerializedName("budget") val budget: Int,
        @SerializedName("genres") val genres: List<Genre>,
        @SerializedName("homepage") val homepage: String,
        @SerializedName("id") val id: Int,
        @SerializedName("imdb_id") val imdbId: String,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("original_title") val originalTitle: String,
        @SerializedName("overview") val overview: String,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("poster_path") val posterPath: Any,
        @SerializedName("production_companies") val productionCompanies: List<Company>,
        @SerializedName("production_countries") val productionCountries: List<Country>,
        @SerializedName("release_date") val releaseDate: String,
        @SerializedName("revenue") val revenue: Int,
        @SerializedName("runtime") val runtime: Int,
        @SerializedName("spoken_languages") val spokenLanguages: List<Language>,
        @SerializedName("status") val status: String,
        @SerializedName("tagline") val tagline: String,
        @SerializedName("title") val title: String,
        @SerializedName("video") val video: Boolean,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("vote_count") val voteCount: Int)