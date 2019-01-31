package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName
import com.shykun.volodymyr.movieviewer.data.entity.*

data class TvDetailsResponse(
        @SerializedName("backdrop_path") var backdropPath: String,
        @SerializedName("created_by") var createdBy: List<Author>,
        @SerializedName("episode_run_time") var episodeRunTime: List<Int>,
        @SerializedName("first_air_date") var firstAirDate: String,
        @SerializedName("genres") var genres: ArrayList<Genre>,
        @SerializedName("homepage") var homepage: String,
        @SerializedName("id") var id: Int,
        @SerializedName("in_production") var inProduction: Boolean,
        @SerializedName("languages") var languages: List<String>,
        @SerializedName("last_air_date") var lastAirDate: String,
        @SerializedName("last_episode_to_air") var lastEpisodeToAir: Episode,
        @SerializedName("name") var name: String,
        @SerializedName("next_episode_to_air") var nextEpisodeToAir: Episode,
        @SerializedName("networks") var networks: List<Company>,
        @SerializedName("number_of_episodes") var numberOfEpisodes: Int,
        @SerializedName("number_of_seasons") var numberOfSeasons: Int,
        @SerializedName("origin_country") var originCountry: List<String>,
        @SerializedName("original_language") var originalLanguage: String,
        @SerializedName("original_name") var originalName: String,
        @SerializedName("overview") var overview: String,
        @SerializedName("popularity") var popularity: Double,
        @SerializedName("poster_path") var posterPath: String,
        @SerializedName("production_companies") var productionCompanies: List<Company>,
        @SerializedName("seasons") var seasons: List<Season>,
        @SerializedName("status") var status: String,
        @SerializedName("type") var type: String,
        @SerializedName("vote_average") var voteAverage: Float,
        @SerializedName("vote_count") var voteCount: Int
)