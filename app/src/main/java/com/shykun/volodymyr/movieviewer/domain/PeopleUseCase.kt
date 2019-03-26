package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import javax.inject.Inject

class PeopleUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun getPopularPeople(page: Int) = apiClient.getPopularPeople(page)
}