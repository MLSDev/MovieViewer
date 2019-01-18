package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter

class RecommendedMoviesAdapter(val movies: ArrayList<Movie>)
    : BaseRecyclerViewAdapter<Movie, RecommendedMoviesViewHolder>(movies) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedMoviesViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_horizontal_item, parent, false)

        return RecommendedMoviesViewHolder(view)
    }

    fun addMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }
}