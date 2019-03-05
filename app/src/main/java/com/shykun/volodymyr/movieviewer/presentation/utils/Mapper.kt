package com.shykun.volodymyr.movieviewer.presentation.utils

import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.*
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalListItem
import com.shykun.volodymyr.movieviewer.presentation.model.ItemType
import com.shykun.volodymyr.movieviewer.presentation.model.SearchListItem

fun topRatedMovieToHorizontalListItem(movie: Movie) = with(movie) {
    HorizontalListItem(id, ItemType.MOVIE, posterPath, R.drawable.ic_star, voteAverage.toString())
}

fun popularMovieToHorizontalListItem(movie: Movie, position: Int) = with(movie) {
    HorizontalListItem(id, ItemType.MOVIE, posterPath, null, "#${position + 1}")
}

fun upcomingMovieToHorizontalListItem(movie: Movie) = with(movie) {
    HorizontalListItem(id, ItemType.MOVIE, posterPath, null, releaseDate)
}

fun topRatedTvToHorizontalListItem(tv: Tv) = with(tv) {
    HorizontalListItem(id, ItemType.TV, posterPath, R.drawable.ic_star, voteAverage.toString())
}

fun popularTvToHorizontalListItem(tv: Tv, position: Int) = with(tv) {
    HorizontalListItem(id, ItemType.TV, posterPath, null, "#${position + 1}")
}

fun tvOnTheAirToHorizontalListItem(tv: Tv) = with(tv) {
    HorizontalListItem(id, ItemType.TV, posterPath, null, firstAirDate)
}

fun actorToHorizontalListItem(actor: Actor) = with(actor) {
    HorizontalListItem(id, ItemType.PERSON, profilePath, null, name)
}

fun roleToHorizontalListItem(role: Role) = with(role) {
    val itemType = if (role.mediaType == "movie") ItemType.MOVIE else ItemType.TV
    HorizontalListItem(id, itemType, posterPath, R.drawable.ic_star, voteAverage.toString())
}


fun movieToSearchListItem(movie: Movie) = with(movie) {
    SearchListItem(id, ItemType.MOVIE, posterPath, title)
}

fun tvToSearchListItem(tv: Tv) = with(tv) {
    SearchListItem(id, ItemType.TV, posterPath, name)
}

fun personToSearchListItem(person: Person) = with(person) {
    SearchListItem(id, ItemType.PERSON, profilePath, name)
}
