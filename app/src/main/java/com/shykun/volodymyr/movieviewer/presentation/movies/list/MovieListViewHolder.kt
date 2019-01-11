package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.Genre
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp
import kotlinx.android.synthetic.main.view_holder_movie.view.*

open class BaseMovieListViewHolder(itemView: View)
    : BaseViewHolder<Movie>(itemView)

class MovieListLoadingViewHolder(itemView: View)
    : BaseMovieListViewHolder(itemView)

class MovieListViewHolder(itemView: View, val moviesType: MoviesType)
    : BaseMovieListViewHolder(itemView) {

    private val poster: ImageView = itemView.poster
    private val title: TextView = itemView.title
    private val rating: TextView = itemView.rating
    private val popularity: TextView = itemView.popularity
    private val genres: TextView = itemView.genres
    private val releaseDate: TextView = itemView.releaseDate

    override fun bind(item: Movie, position: Int) {
        super.bind(item, position)

        title.text = item.title
        rating.text = item.voteAverage.toString()
        genres.text = Genre.getGenres(item)

        when (moviesType) {
            MoviesType.TOP_RATED -> {
                popularity.visibility = View.GONE
                releaseDate.visibility = View.GONE
            }
            MoviesType.POPULAR -> {
                popularity.text = "#${position + 1}"
                releaseDate.visibility = View.GONE
            }
            MoviesType.UPCOMING -> {
                releaseDate.text = item.releaseDate
                popularity.visibility = View.GONE
            }
        }

        GlideApp.with(itemView)
                .load("http://image.tmdb.org/t/p/w185${item.posterPath}")
                .into(poster)

    }

}