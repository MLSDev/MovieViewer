package com.shykun.volodymyr.movieviewer.data.network.body

import com.google.gson.annotations.SerializedName

data class AddToWatchlistBody(
        @SerializedName("media_type") var mediaType: String,
        @SerializedName("media_id") var mediaId: Int,
        @SerializedName("watchlist") var watchlist: Boolean
)