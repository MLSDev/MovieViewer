package com.shykun.volodymyr.movieviewer.data.network.body

import com.google.gson.annotations.SerializedName

data class MarkAsFavoriteBody(
        @SerializedName("media_type") var mediaType: String,
        @SerializedName("media_id") var mediaId: Int,
        @SerializedName("favorite") var favorite: Boolean
)