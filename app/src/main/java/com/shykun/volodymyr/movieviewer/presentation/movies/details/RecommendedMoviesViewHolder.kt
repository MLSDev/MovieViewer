package com.shykun.volodymyr.movieviewer.presentation.movies.details

import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.databinding.ItemRecommendedMoviesBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class RecommendedMoviesViewHolder(
        private val binding: ItemRecommendedMoviesBinding,
        private val clickSubject: PublishSubject<Int>) : BaseViewHolder<Movie>(binding) {

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