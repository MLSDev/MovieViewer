package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.data.network.body.RateBody
import javax.inject.Inject

class TvDetailsUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun getTvDetails(tvId: Int) = apiClient.getTvDetails(tvId)

    fun getTvCast(tvId: Int) = apiClient.getTvCredits(tvId).map { it.cast }

    fun getRecommendedTv(tvId: Int) = apiClient.getRecommedndedTv(tvId).map { it.results }

    fun getTvReviews(tvId: Int) = apiClient.getTvReviews(tvId).map { it.results }

    fun rateTv(tvId: Int, rating: Float, sessionId: String) = apiClient.rateTv(tvId, RateBody(rating), sessionId)
}