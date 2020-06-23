package com.shykun.volodymyr.movieviewer.data.entity

import com.google.gson.annotations.SerializedName

data class Actor(
    @SerializedName("cast_id") var castId: Int,
    @SerializedName("character") var character: String,
    @SerializedName("credit_id") var creditId: String,
    @SerializedName("gender") var gender: Int,
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("order") var order: Int,
    @SerializedName("profile_path") var profilePath: String
)