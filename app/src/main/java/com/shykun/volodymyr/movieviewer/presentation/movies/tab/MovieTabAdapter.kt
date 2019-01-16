package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

class MovieTabAdapter(items: ArrayList<Movie>): BaseRecyclerViewAdapter<Movie, MovieTabViewHolder>(items) {

    private val movieClickSubject = PublishSubject.create<Int>()

    val movieClickEvent: Observable<Int> = movieClickSubject
    var type: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTabViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_horizontal_item, parent, false)

        return MovieTabViewHolder(view, movieClickSubject)
    }

    override fun onBindViewHolder(viewHolder: MovieTabViewHolder, position: Int) {
        viewHolder.type = this.type
        super.onBindViewHolder(viewHolder, position)
    }
}



