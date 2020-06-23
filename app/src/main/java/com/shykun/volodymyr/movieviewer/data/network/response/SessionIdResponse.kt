package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName


data class SessionIdResponse(
        @SerializedName("success") val success: Boolean,
        @SerializedName("session_id") val sessionId: String
)