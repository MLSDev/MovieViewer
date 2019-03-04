package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.view.View
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalMovieBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class MovieTabViewHolder(
        private val binding: ItemHorizontalMovieBinding,
        private val movieClickSubject: PublishSubject<Int>,
        private val moviesType: MoviesType) : BaseViewHolder<Movie>(binding) {

    lateinit var footerText: String
    var footerIconVisibility = View.VISIBLE

    override fun bind(item: Movie?) {
        super.bind(item)

        if (moviesType != MoviesType.TOP_RATED)
            footerIconVisibility = View.GONE

        footerText = when (moviesType) {
            MoviesType.POPULAR -> "#${adapterPosition + 1}"
            MoviesType.TOP_RATED -> item?.voteAverage.toString()
            MoviesType.UPCOMING -> item?.releaseDate ?: ""
            else -> ""
        }

        itemView.setOnClickListener {
            if (item != null) {
                movieClickSubject.onNext(item.id)
            }
        }

        executeBinding()
    }

    private fun executeBinding() {
        binding.movie = item
        binding.movieViewHolder = this
        binding.executePendingBindings()
    }
}