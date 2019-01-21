package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName


data class PersonDetailsResponse(
    @SerializedName("birthday") var birthday: String,
    @SerializedName("known_for_department") var knownForDepartment: String,
    @SerializedName("deathday") var deathday: String,
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("also_known_as") var alsoKnownAs: List<String>,
    @SerializedName("gender") var gender: Int,
    @SerializedName("biography") var biography: String,
    @SerializedName("popularity") var popularity: Double,
    @SerializedName("place_of_birth") var placeOfBirth: String,
    @SerializedName("profile_path") var profilePath: String,
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("imdb_id") var imdbId: String,
    @SerializedName("homepage") var homepage: Any)