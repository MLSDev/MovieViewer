package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.databinding.ViewDataBinding
import android.view.View
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.databinding.ItemLoadingBinding
import com.shykun.volodymyr.movieviewer.databinding.ItemMovieBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject

open class BaseMovieListViewHolder(viewDataBinding: ViewDataBinding)
    : BaseViewHolder<Movie>(viewDataBinding)

class MovieListLoadingViewHolder(binding: ItemLoadingBinding)
    : BaseMovieListViewHolder(binding)

class MovieListViewHolder(
        private val binding: ItemMovieBinding,
        private val moviesType: MoviesType,
        private val movieClickSubject: PublishSubject<Int>)
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

        itemView.setOnClickListener { movieClickSubject.onNext(item.id) }

        executeBinding(item)
    }

    private fun executeBinding(movie: Movie) {
        binding.movie = movie
        binding.viewHolder = this
        binding.executePendingBindings()
    }
}