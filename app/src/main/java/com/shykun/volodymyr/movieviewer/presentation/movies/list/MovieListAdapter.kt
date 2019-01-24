package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderLoadingBinding
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderMovieBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

private const val MOVIE = 0
private const val LOADING = 1

class MovieListAdapter(itemList: ArrayList<Movie>, val moviesType: MoviesType)
    : BaseRecyclerViewAdapter<Movie, BaseMovieListViewHolder>(itemList) {

    private val movieClickSubject = PublishSubject.create<Int>()
    val movieClickEvent: Observable<Int> = movieClickSubject
    var lastLoadedPage = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMovieListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == MOVIE) {
            val binding = DataBindingUtil.inflate<ViewHolderMovieBinding>(
                    inflater,
                    R.layout.view_holder_movie,
                    parent,
                    false)
            return MovieListViewHolder(binding, moviesType, movieClickSubject)
        } else {
            val binding = DataBindingUtil.inflate<ViewHolderLoadingBinding>(
                    inflater,
                    R.layout.view_holder_loading,
                    parent,
                    false)
            return MovieListLoadingViewHolder(binding)
        }
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

    fun addMovies(movies: List<Movie>) {
        items.addAll(movies)
        notifyDataSetChanged()
    }

    fun setMovies(movies: List<Movie>) {
        items.clear()
        addMovies(movies)
    }
}