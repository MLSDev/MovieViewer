package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.presentation.utils.movieToSearchListItem
import com.shykun.volodymyr.movieviewer.presentation.utils.personToSearchListItem
import com.shykun.volodymyr.movieviewer.presentation.utils.tvToSearchListItem
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun searchMovies(query: String, page: Int = 1) = apiClient.searchMovies(query, page)

    fun searchTv(query: String, page: Int = 1) = apiClient.searchTv(query, page)

    fun searchPeople(query: String, page: Int = 1) = apiClient.searchPeople(query, page)
}