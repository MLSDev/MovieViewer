package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName
import com.shykun.volodymyr.movieviewer.data.entity.Actor

data class GetMovieCreditsResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("cast") val cast: List<Actor>
)