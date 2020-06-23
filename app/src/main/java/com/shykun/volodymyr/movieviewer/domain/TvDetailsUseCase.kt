package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.data.network.body.AddToWatchlistBody
import com.shykun.volodymyr.movieviewer.data.network.body.MarkAsFavoriteBody
import com.shykun.volodymyr.movieviewer.data.network.body.RateBody
import javax.inject.Inject

class TvDetailsUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun getTvDetails(tvId: Int) = apiClient.getTvDetails(tvId)
    fun getTvCast(tvId: Int) = apiClient.getTvCredits(tvId)
    fun getRecommendedTv(tvId: Int) = apiClient.getRecommedndedTv(tvId)
    fun getTvReviews(tvId: Int) = apiClient.getTvReviews(tvId)

    fun rateTv(tvId: Int, rating: Float, sessionId: String) = apiClient.rateTv(tvId, RateBody(rating), sessionId)
    fun addToFavorites(tvId: Int, sessionId: String) = apiClient.markAsFavorite(MarkAsFavoriteBody("tv", tvId, true), sessionId)
    fun addToWatchlist(tvId: Int, sessionId: String) = apiClient.addToWatchlist(AddToWatchlistBody("tv", tvId, true), sessionId)

    fun deleteTvRating(tvId: Int, sessionId: String) = apiClient.deleteTvRating(tvId, sessionId)
    fun deleteFromFavorites(tvId: Int, sessionId: String) = apiClient.markAsFavorite(MarkAsFavoriteBody("tv", tvId, false), sessionId)
    fun deleteFromWatchlist(tvId: Int, sessionId: String) = apiClient.addToWatchlist(AddToWatchlistBody("tv", tvId, false), sessionId)

    fun getTvAccountStates(tvId: Int, sessionId: String) = apiClient.getTvAccountStates(tvId, sessionId)
}