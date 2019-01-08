package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.entity.TVType
import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import javax.inject.Inject

class GetTVUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun getTV(tvType: TVType) = apiClient.getTV(tvType).map { it.results }
}
