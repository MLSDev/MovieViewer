package com.shykun.volodymyr.movieviewer.domain

import com.shykun.volodymyr.movieviewer.data.network.ApiClient
import com.shykun.volodymyr.movieviewer.data.network.body.AddToWatchlistBody
import com.shykun.volodymyr.movieviewer.data.network.body.MarkAsFavoriteBody
import com.shykun.volodymyr.movieviewer.data.network.body.RateBody
import com.shykun.volodymyr.movieviewer.data.network.response.ItemAccountStateResponse
import com.shykun.volodymyr.movieviewer.presentation.utils.actorToHorizontalListItem
import com.shykun.volodymyr.movieviewer.presentation.utils.topRatedMovieToHorizontalListItem
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(private val apiClient: ApiClient) {

    fun getMovieDetails(movieId: Int) = apiClient.getMovieDetails(movieId)
    fun getMovieCredits(movieId: Int) = apiClient.getMovieCredits(movieId).map { it.cast.map { actorToHorizontalListItem(it) } }
    fun getMovieReviews(movieId: Int) = apiClient.getMovieReviews(movieId).map { it.results }
    fun getRecommendedMovies(movieId: Int) = apiClient.getRecommendedMovies(movieId).map { it.results.map { topRatedMovieToHorizontalListItem(it) } }

    fun rateMovie(movieId: Int, rating: Float, sessionId: String) = apiClient.rateMovie(movieId, RateBody(rating), sessionId)
    fun markAsFavorite(movieId: Int, sessionId: String) = apiClient.markAsFavorite(MarkAsFavoriteBody("movie", movieId, true), sessionId)
    fun addToWatchlist(movieId: Int, sessionId: String) = apiClient.addToWatchlist(AddToWatchlistBody("movie", movieId, true), sessionId)

    fun deleteMovieRating(movieId: Int, sessionId: String) = apiClient.deleteMovieRating(movieId, sessionId)
    fun deleteFromFavorites(movieId: Int, sessionId: String) = apiClient.markAsFavorite(MarkAsFavoriteBody("movie", movieId, false), sessionId)
    fun removeFromWatchList(movieId: Int, sessionId: String) = apiClient.addToWatchlist(AddToWatchlistBody("movie", movieId, false), sessionId)

    fun getMovieAccountStates(movieId: Int, sessionId: String) = apiClient.getMovieAccountStates(movieId, sessionId)
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