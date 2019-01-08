package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import javax.inject.Inject

class GetTVUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun execute(tvType: TvType) = apiClient.getTV(tvType).map { it.results }
}
