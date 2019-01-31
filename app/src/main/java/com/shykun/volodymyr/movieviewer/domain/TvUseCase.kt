package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import javax.inject.Inject

class TvUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun getTv(tvType: TvType, page: Int) = apiClient.getTV(tvType, page).map { it.results }

    fun searchTv(query: String, page: Int = 1) = apiClient.searchTv(query, page).map { it.results }
}
