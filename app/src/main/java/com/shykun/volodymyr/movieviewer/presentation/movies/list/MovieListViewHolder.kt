package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.Genre
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_holder_movie.view.*

open class BaseMovieListViewHolder(itemView: View)
    : BaseViewHolder<Movie>(itemView)

class MovieListLoadingViewHolder(itemView: View)
    : BaseMovieListViewHolder(itemView)

class MovieListViewHolder(itemView: View)
    : BaseMovieListViewHolder(itemView) {

    private val poster: ImageView = itemView.poster
    private val title: TextView = itemView.title
    private val rating: TextView = itemView.rating
    private val popularity: TextView = itemView.popularity
    private val genres: TextView = itemView.genres


    override fun bind(item: Movie, position: Int) {
        super.bind(item, position)

        title.text = item.title
        rating.text = item.voteAverage.toString()
        popularity.text = "#${position + 1}"
        genres.text = Genre.getGenres(item)
        GlideApp.with(itemView)
                .load("http://image.tmdb.org/t/p/w185${item.posterPath}")
                .into(poster)

    }

}