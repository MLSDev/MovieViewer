package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderRecommendedMoviesItemBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter

class RecommendedMoviesAdapter(val movies: ArrayList<Movie>)
    : BaseRecyclerViewAdapter<Movie, RecommendedMoviesViewHolder>(movies) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedMoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewHolderRecommendedMoviesItemBinding>(
                inflater,
                R.layout.view_holder_recommended_movies_item,
                parent,
                false)

        return RecommendedMoviesViewHolder(binding)
    }

    fun addMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }
}