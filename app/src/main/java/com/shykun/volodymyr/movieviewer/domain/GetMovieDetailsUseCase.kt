package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun execute(movieId: Int) = apiClient.getMovieDetails(movieId)
}