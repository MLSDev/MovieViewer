package com.shykun.volodymyr.movieviewer.data.network.body

import com.google.gson.annotations.SerializedName

data class RateBody(@SerializedName("value") val value: Float)