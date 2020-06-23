package com.shykun.volodymyr.movieviewer.data.entity

import com.google.gson.annotations.SerializedName


data class Country(
    @SerializedName("iso_3166_1") val iso31661: String,
    @SerializedName("name") val name: String)