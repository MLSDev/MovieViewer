package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import javax.inject.Inject

class DiscoverUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun discoverMovies(year: Int?, rating: Int?, genres: String?, page:Int) = apiClient
            .discoverMovies(year, rating, genres, page)
            .map { it.results }

    fun discoverTv(airDate: String?, voteAverage: Int?, genres: String?, page: Int) = apiClient
            .discoverTv(airDate, voteAverage, genres, page)
            .map { it.results }
}