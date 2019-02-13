package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun getRequestToken() = apiClient.getRequstToken()
}