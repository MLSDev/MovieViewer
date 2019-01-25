package com.shykun.volodymyr.movieviewer.presentation.movies.details

import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.databinding.ItemRecommendedMoviesBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder

class RecommendedMoviesViewHolder(private val binding: ItemRecommendedMoviesBinding) : BaseViewHolder<Movie>(binding) {

    override fun bind(item: Movie, position: Int) {
        super.bind(item, position)

        executeBinding(item)
    }

    private fun executeBinding(movie: Movie) {
        binding.movie = movie
        binding.executePendingBindings()
    }


}