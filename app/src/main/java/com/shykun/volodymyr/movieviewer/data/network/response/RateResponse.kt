package com.shykun.volodymyr.movieviewer.data.network.response

import com.google.gson.annotations.SerializedName


data class RateResponse(
        @SerializedName("status_code") val statusCode: Int,
        @SerializedName("status_message") val statusMessage: String)