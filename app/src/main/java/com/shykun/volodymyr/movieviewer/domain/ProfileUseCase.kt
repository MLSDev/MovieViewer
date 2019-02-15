package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.data.network.body.RequestTokenBody
import com.shykun.volodymyr.movieviewer.data.network.response.RequestTokenResponse
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun getRequestToken() = apiClient.getRequstToken()

    fun createSessionId(requestToken: String) = apiClient.createSessionId(RequestTokenBody(requestToken))

    fun getAccountDetails(sessionId: String) = apiClient.getAccountDetails(sessionId)
}