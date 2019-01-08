package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import javax.inject.Inject

class GetPeopleUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun execute() = apiClient.getPopularPeople().map { it.results }
}