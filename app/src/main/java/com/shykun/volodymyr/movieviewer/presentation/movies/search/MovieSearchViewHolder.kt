package com.shykun.volodymyr.movieviewer.presentation.movies.search

import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.databinding.ItemSearchMovieBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class MovieSearchViewHolder(
        private val binding: ItemSearchMovieBinding,
        private val clickSubject: PublishSubject<Int>): BaseViewHolder<Movie>(binding) {

    override fun bind(item: Movie?, position: Int) {
        super.bind(item, position)

        itemView.setOnClickListener {
            if (item != null) {
                clickSubject.onNext(item.id)
            }
        }

        executeBinding(item)
    }

    private fun executeBinding(movie: Movie?) {
        binding.movie = movie
        binding.executePendingBindings()
    }
}