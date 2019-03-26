package com.shykun.volodymyr.movieviewer.presentation.utils

import com.google.gson.JsonElement
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.*
import com.shykun.volodymyr.movieviewer.data.network.response.ItemAccountStateResponse
import com.shykun.volodymyr.movieviewer.data.network.response.MoviesResponse
import com.shykun.volodymyr.movieviewer.data.network.response.TvResponse
import com.shykun.volodymyr.movieviewer.presentation.model.*
import io.reactivex.functions.Function

fun topRatedMovieToHorizontalListItem(movie: Movie) = with(movie) {
    HorizontalItem(id, ItemType.MOVIE, posterPath, R.drawable.ic_star, voteAverage.toString())
}

fun popularMovieToHorizontalListItem(movie: Movie, position: Int) = with(movie) {
    HorizontalItem(id, ItemType.MOVIE, posterPath, null, "#${position + 1}")
}

fun upcomingMovieToHorizontalListItem(movie: Movie) = with(movie) {
    HorizontalItem(id, ItemType.MOVIE, posterPath, null, releaseDate)
}

fun topRatedTvToHorizontalListItem(tv: Tv) = with(tv) {
    HorizontalItem(id, ItemType.TV, posterPath, R.drawable.ic_star, voteAverage.toString())
}

fun popularTvToHorizontalListItem(tv: Tv, position: Int) = with(tv) {
    HorizontalItem(id, ItemType.TV, posterPath, null, "#${position + 1}")
}

fun tvOnTheAirToHorizontalListItem(tv: Tv) = with(tv) {
    HorizontalItem(id, ItemType.TV, posterPath, null, firstAirDate)
}

fun actorToHorizontalListItem(actor: Actor) = with(actor) {
    HorizontalItem(id, ItemType.PERSON, profilePath, null, name)
}

fun roleToHorizontalListItem(role: Role) = with(role) {
    val itemType = if (role.mediaType == "movie") ItemType.MOVIE else ItemType.TV
    HorizontalItem(id, itemType, posterPath, R.drawable.ic_star, voteAverage.toString())
}


fun movieToSearchListItem(movie: Movie) = with(movie) {
    SearchItem(id, ItemType.MOVIE, posterPath, title)
}

fun tvToSearchListItem(tv: Tv) = with(tv) {
    SearchItem(id, ItemType.TV, posterPath, name)
}

fun personToSearchListItem(person: Person) = with(person) {
    SearchItem(id, ItemType.PERSON, profilePath, name)
}


fun movieToVerticalItem(movie: Movie) = with(movie) {
    VerticalItem(ItemType.MOVIE, id, title, posterPath, voteAverage, genreIds, releaseDate)
}

fun tvToVerticalItem(tv: Tv) = with(tv) {
    VerticalItem(ItemType.TV, id, name, posterPath, voteAverage, genreIds, firstAirDate)
}

fun movieResponseToVerticalItemList(moviesResponse: MoviesResponse) = with(moviesResponse) {
    VerticalItemList(moviesResponse.results.map { movieToVerticalItem(it) }, totalResults)
}

fun tvResponseToVerticalItemList(tvResponse: TvResponse) = with(tvResponse) {
    VerticalItemList(tvResponse.results.map { tvToVerticalItem(it) }, totalResults)
}

fun jsonElementToItemAccountStateResponse(jsonElement: JsonElement) = with(jsonElement) {
    val json = jsonElement.asJsonObject
    val id = json.getAsJsonPrimitive("id").asInt
    val favorite = json.getAsJsonPrimitive("favorite").asBoolean
    val watchlist = json.getAsJsonPrimitive("watchlist").asBoolean
    val rated = json.get("rated").isJsonObject

    ItemAccountStateResponse(id, favorite, rated, watchlist)
}