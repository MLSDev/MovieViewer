package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun searchMovies(query: String, page: Int = 1) = apiClient.searchMovies(query, page).map { it.results }
}