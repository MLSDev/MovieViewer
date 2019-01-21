package com.shykun.volodymyr.movieviewer.data.entity

import com.google.gson.annotations.SerializedName

data class Author (
    @SerializedName("id") val id: Int,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("name") var name: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("profile_path") val profilePath: String)