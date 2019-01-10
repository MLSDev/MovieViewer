package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter

class MovieTabAdapter(items: ArrayList<Movie>): BaseRecyclerViewAdapter<Movie, MovieTabViewHolder>(items) {

    var type: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTabViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_horizontal_item, parent, false)

        return MovieTabViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MovieTabViewHolder, position: Int) {
        viewHolder.type = this.type
        super.onBindViewHolder(viewHolder, position)
    }
}



