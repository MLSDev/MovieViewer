package com.shykun.volodymyr.movieviewer.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter
import javax.inject.Inject

class GeneralMoviesAdapter(items: ArrayList<ArrayList<Movie>>)
    : BaseRecyclerViewAdapter<ArrayList<Movie>, GeneralMoviesViewHolder>(items) {

    init {
        items.add(ArrayList())
        items.add(ArrayList())
        items.add(ArrayList()) //TODO: убрать этот костыль
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralMoviesViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_horizontal_list, parent, false)

        return GeneralMoviesViewHolder(view, clickSubject)
    }

    fun addMovies(movies: ArrayList<Movie>, position: Int) {
        items[position].addAll(movies)
        notifyDataSetChanged()
    }
}