package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import javax.inject.Inject

class PeopleUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun getPopularPeople(page: Int) = apiClient.getPopularPeople(page).map { it.results }

    fun searchPeople(query: String, page: Int = 1) = apiClient.searchPeople(query, page).map { it.results }
}