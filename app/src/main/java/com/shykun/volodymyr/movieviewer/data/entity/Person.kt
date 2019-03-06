package com.shykun.volodymyr.movieviewer.data.entity

import com.google.gson.annotations.SerializedName

data class Person(
        @SerializedName("profile_path") val profilePath: String,
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("id") val id: Int,
        @SerializedName("known_for") val knownFor: List<Picture>,
        @SerializedName("name") val name: String,
        @SerializedName("popularity") val popularity: Double
)

enum class PeopleType {
    POPULAR, SEARCHED
}