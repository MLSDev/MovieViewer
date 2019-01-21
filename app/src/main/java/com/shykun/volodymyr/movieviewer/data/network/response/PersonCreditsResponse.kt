package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName
import com.shykun.volodymyr.movieviewer.data.entity.Role

class PersonCreditsResponse (
        @SerializedName("cast") val cast: List<Role>,
        @SerializedName("id") val id: Int)