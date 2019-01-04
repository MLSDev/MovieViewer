package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.data.network.response.TVType
import javax.inject.Inject

class GetTVUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun getTV(tvType: TVType) = apiClient.getTV(tvType).map { it.results }
}
