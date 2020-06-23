package com.shykun.volodymyr.movieviewer.data.network.body

import com.google.gson.annotations.SerializedName

data class RequestTokenBody (
        @SerializedName("request_token") val requestToken: String
)