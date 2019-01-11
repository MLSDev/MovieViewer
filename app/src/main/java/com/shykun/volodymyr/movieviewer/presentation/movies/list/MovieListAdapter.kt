package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter

private const val MOVIE = 0
private const val LOADING = 1

class MovieListAdapter(itemList: ArrayList<Movie>, val moviesType: MoviesType)
    : BaseRecyclerViewAdapter<Movie, BaseMovieListViewHolder>(itemList) {

    var lastLoadedPage = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMovieListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == MOVIE)
            MovieListViewHolder(inflater.inflate(R.layout.view_holder_movie, parent, false), moviesType)
        else
            MovieListLoadingViewHolder(inflater.inflate(R.layout.view_holder_loading, parent, false))
    }

    override fun onBindViewHolder(viewHolder: BaseMovieListViewHolder, position: Int) {
        if (position < items.size) {
            super.onBindViewHolder(viewHolder, position)
        }
    }

    override fun getItemCount() = items.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position < items.size)
            MOVIE
        else
            LOADING
    }

    fun addMovies(movies: ArrayList<Movie>) {
        items.addAll(movies)
        notifyDataSetChanged()

    }
}