package com.shykun.volodymyr.movieviewer.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Person(
        @SerializedName("profile_path") var profilePath: String,
        @SerializedName("adult") var adult: Boolean,
        @SerializedName("id") var id: Int,
        @SerializedName("known_for") var knownFor: List<Picture>,
        @SerializedName("name") var name: String,
        @SerializedName("popularity") var popularity: Double
)