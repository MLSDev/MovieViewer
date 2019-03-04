package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.databinding.ItemLoadingBinding
import com.shykun.volodymyr.movieviewer.databinding.ItemMovieBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

private const val MOVIE = 0
private const val LOADING = 1

class MovieListAdapter(val moviesType: MoviesType)
    : BaseRecyclerViewAdapter<Movie, BaseMovieListViewHolder>() {

    private val clickSubject = PublishSubject.create<Int>()
    val clickEvent: Observable<Int> = clickSubject
    var nextPage = 1
    var totalItemsCount = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMovieListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == MOVIE) {
            val binding = DataBindingUtil.inflate<ItemMovieBinding>(
                    inflater,
                    R.layout.item_movie,
                    parent,
                    false)
            MovieListViewHolder(binding, moviesType, clickSubject)
        } else {
            val binding = DataBindingUtil.inflate<ItemLoadingBinding>(
                    inflater,
                    R.layout.item_loading,
                    parent,
                    false)
            MovieListLoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(viewHolder: BaseMovieListViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE)
            super.onBindViewHolder(viewHolder, position)
        else
            (viewHolder as MovieListLoadingViewHolder).bind(null, totalItemsCount)
    }

    override fun getItemCount() = items.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position < items.size)
            MOVIE
        else
            LOADING
    }
}
