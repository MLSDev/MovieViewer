package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalMovieBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

class MovieTabAdapter(items: ArrayList<Movie>): BaseRecyclerViewAdapter<Movie, MovieTabViewHolder>(items) {

    private val movieClickSubject = PublishSubject.create<Int>()

    val movieClickEvent: Observable<Int> = movieClickSubject
    var type: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTabViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHorizontalMovieBinding>(
                inflater,
                R.layout.item_horizontal_movie,
                parent,
                false)

        return MovieTabViewHolder(binding, movieClickSubject)
    }

    override fun onBindViewHolder(viewHolder: MovieTabViewHolder, position: Int) {
        viewHolder.type = this.type
        super.onBindViewHolder(viewHolder, position)
    }
}



