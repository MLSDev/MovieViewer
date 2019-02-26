package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName


data class AccountDetailsResponse(
        @SerializedName("avatar") var avatar: Avatar,
        @SerializedName("id") var id: Int,
        @SerializedName("iso_639_1") var iso6391: String,
        @SerializedName("iso_3166_1") var iso31661: String,
        @SerializedName("name") var name: String,
        @SerializedName("include_adult") var includeAdult: Boolean,
        @SerializedName("username") var username: String
)

data class Avatar(@SerializedName("gravatar") val gravatar: Gravatar)

data class Gravatar(@SerializedName("hash") val hash: String)
