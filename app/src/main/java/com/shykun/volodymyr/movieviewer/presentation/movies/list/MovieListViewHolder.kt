package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.databinding.ViewDataBinding
import android.view.View
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderLoadingBinding
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderMovieBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder

open class BaseMovieListViewHolder(viewDataBinding: ViewDataBinding)
    : BaseViewHolder<Movie>(viewDataBinding)

class MovieListLoadingViewHolder(binding: ViewHolderLoadingBinding)
    : BaseMovieListViewHolder(binding)

class MovieListViewHolder(private val binding: ViewHolderMovieBinding, val moviesType: MoviesType)
    : BaseMovieListViewHolder(binding) {

    var popularityVisibility = View.VISIBLE
    var releaseDateVisibility = View.VISIBLE

    var popularity: String = ""

    override fun bind(item: Movie, position: Int) {
        super.bind(item, position)

        when (moviesType) {
            MoviesType.TOP_RATED -> {
                popularityVisibility = View.GONE
                releaseDateVisibility = View.GONE
            }
            MoviesType.POPULAR -> {
                popularity = "#${position + 1}"
                releaseDateVisibility = View.GONE
            }
            MoviesType.UPCOMING -> {
                popularityVisibility = View.GONE
            }
        }

        executeBinding(item)
    }

    private fun executeBinding(movie: Movie) {
        binding.movie = movie
        binding.viewHolder = this
        binding.executePendingBindings()
    }
}