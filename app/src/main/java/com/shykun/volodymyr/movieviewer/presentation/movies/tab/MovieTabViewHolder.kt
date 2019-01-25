package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.view.View
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalMovieBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class MovieTabViewHolder(
        private val binding: ItemHorizontalMovieBinding,
        private val movieClickSubject: PublishSubject<Int>) : BaseViewHolder<Movie>(binding) {

    var type = -1
    lateinit var footerText: String
    var footerIconVisibility = View.VISIBLE

    override fun bind(item: Movie, position: Int) {
        super.bind(item, position)

        if (type != TOP_RATED_MOVIES)
            footerIconVisibility = View.GONE

        footerText = when (type) {
            POPULAR_MOVIES -> "#${position + 1}"
            TOP_RATED_MOVIES -> item.voteAverage.toString()
            UPCOMING_MOVIES -> item.releaseDate
            else -> ""
        }

        itemView.setOnClickListener { movieClickSubject.onNext(item.id) }

        executeBinding(item)
    }

    private fun executeBinding(movie: Movie) {
        binding.movie = movie
        binding.movieViewHolder = this
        binding.executePendingBindings()
    }
}