package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalMovieListBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class GeneralMovieTabAdapter(items: ArrayList<ArrayList<Movie>>)
    : BaseRecyclerViewAdapter<ArrayList<Movie>, GeneralMovieTabViewHolder>(items) {

    private val seeAllClickSubject = PublishSubject.create<Int>()
    private val movieClickSubject = PublishSubject.create<Int>()

    val seeAllClickEvent: Observable<Int> = seeAllClickSubject
    val movieClickEvent: Observable<Int> = movieClickSubject

    init {
        items.add(ArrayList())
        items.add(ArrayList())
        items.add(ArrayList()) //TODO: Remove this workaround
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralMovieTabViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHorizontalMovieListBinding>(
                inflater,
                R.layout.item_horizontal_movie_list,
                parent,
                false)

        return GeneralMovieTabViewHolder(binding, seeAllClickSubject, movieClickSubject)
    }

    fun addMovies(movies: List<Movie>, position: Int) {
        items[position].addAll(movies)
        notifyDataSetChanged()
    }
}