package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import javax.inject.Inject

class DiscoverUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun discoverMovies(year: Int?, rating: Int?, genres: String?) = apiClient
            .discoverMovies(year, rating, genres)
            .map { it.results }

    fun discoverTv(airDate: String?, voteAverage: Int?, genres: String?) = apiClient
            .discoverTv(airDate, voteAverage, genres)
            .map { it.results }
}