package com.shykun.volodymyr.movieviewer.presentation.movies.details

import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderRecommendedMoviesItemBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder

class RecommendedMoviesViewHolder(private val binding: ViewHolderRecommendedMoviesItemBinding) : BaseViewHolder<Movie>(binding) {

    override fun bind(item: Movie, position: Int) {
        super.bind(item, position)

        executeBinding(item)
    }

    private fun executeBinding(movie: Movie) {
        binding.movie = movie
        binding.executePendingBindings()
    }


}