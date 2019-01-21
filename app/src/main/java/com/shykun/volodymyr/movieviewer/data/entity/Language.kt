package com.shykun.volodymyr.movieviewer.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Language(
        @SerializedName("iso_639_1") val iso6391: String,
        @SerializedName("name") val name: String)