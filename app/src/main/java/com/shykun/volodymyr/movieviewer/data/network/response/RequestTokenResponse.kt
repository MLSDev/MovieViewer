package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName

data class RequestTokenResponse(
        @SerializedName("success") var success: Boolean,
        @SerializedName("expires_at") var expiresAt: String,
        @SerializedName("request_token") var requestToken: String
)