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

class MovieListLoadingViewHolder(private val binding: ItemLoadingBinding)
    : BaseMovieListViewHolder(binding) {

    override fun bind(item: Movie?, totalItemsCount: Int) {
        super.bind(item, totalItemsCount)

        if (adapterPosition == totalItemsCount && adapterPosition != 0)
            binding.loadingProgressBar.visibility = View.GONE
    }
}

class MovieListViewHolder(
        private val binding: ItemMovieBinding,
        private val moviesType: MoviesType,
        internal val clickSubject: PublishSubject<Int>)
    : BaseMovieListViewHolder(binding) {

    var popularity = ""
    var popularityVisibility = View.VISIBLE
    var releaseDateVisibility = View.VISIBLE

    override fun bind(item: Movie?, totalItemsCount: Int) {
        super.bind(item, totalItemsCount)

        when (moviesType) {
            MoviesType.TOP_RATED -> {
                popularityVisibility = View.GONE
                releaseDateVisibility = View.GONE
            }
            MoviesType.POPULAR -> {
                popularity = "#${adapterPosition + 1}"
                releaseDateVisibility = View.GONE
            }
            MoviesType.UPCOMING -> {
                popularityVisibility = View.GONE
            }
        }

        itemView.setOnClickListener {
            if (item != null) {
                clickSubject.onNext(item.id)
            }
        }

        executeBinding(item)
    }

    private fun executeBinding(movie: Movie?) {
        binding.movie = movie
        binding.viewHolder = this
        binding.executePendingBindings()
    }
}