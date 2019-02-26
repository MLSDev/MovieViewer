package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.data.network.body.AddToWatchlistBody
import com.shykun.volodymyr.movieviewer.data.network.body.MarkAsFavoriteBody
import com.shykun.volodymyr.movieviewer.data.network.body.RateBody
import com.shykun.volodymyr.movieviewer.data.network.response.ItemAccountStateResponse
import javax.inject.Inject

class TvDetailsUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun getTvDetails(tvId: Int) = apiClient.getTvDetails(tvId)
    fun getTvCast(tvId: Int) = apiClient.getTvCredits(tvId).map { it.cast }
    fun getRecommendedTv(tvId: Int) = apiClient.getRecommedndedTv(tvId).map { it.results }
    fun getTvReviews(tvId: Int) = apiClient.getTvReviews(tvId).map { it.results }

    fun rateTv(tvId: Int, rating: Float, sessionId: String) = apiClient.rateTv(tvId, RateBody(rating), sessionId)
    fun markAsFavorite(tvId: Int, sessionId: String) = apiClient.markAsFavorite(MarkAsFavoriteBody("tv", tvId, true), sessionId)
    fun addToWatchlist(tvId: Int, sessionId: String) = apiClient.addToWatchlist(AddToWatchlistBody("tv", tvId, true), sessionId)

    fun deleteTvRating(tvId: Int, sessionId: String) = apiClient.deleteTvRating(tvId, sessionId)
    fun removeFromFavorites(tvId: Int, sessionId: String) = apiClient.markAsFavorite(MarkAsFavoriteBody("tv", tvId, false), sessionId)
    fun removeFromWatchlist(tvId: Int, sessionId: String) = apiClient.addToWatchlist(AddToWatchlistBody("tv", tvId, false), sessionId)

    fun getTvAccountStates(tvId: Int, sessionId: String) = apiClient.getTvAccountStates(tvId, sessionId)
            .map {
                jsonElement ->
                val json = jsonElement.asJsonObject
                val id = json.getAsJsonPrimitive("id").asInt
                val favorite = json.getAsJsonPrimitive("favorite").asBoolean
                val watchlist = json.getAsJsonPrimitive("watchlist").asBoolean
                val rated = json.get("rated").isJsonObject

                ItemAccountStateResponse(id, favorite, rated, watchlist)
            }
}