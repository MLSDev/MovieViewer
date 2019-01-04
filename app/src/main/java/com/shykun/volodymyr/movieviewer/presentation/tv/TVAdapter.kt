package com.shykun.volodymyr.movieviewer.presentation.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.TV
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter
import com.shykun.volodymyr.movieviewer.presentation.movies.MovieViewHolder

class TVAdapter(items: ArrayList<TV>): BaseRecyclerViewAdapter<TV, TVViewHolder>(items) {

    var type: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_horizontal_item, parent, false)

        return TVViewHolder(view, clickSubject)
    }

    override fun onBindViewHolder(viewHolder: TVViewHolder, position: Int) {
        viewHolder.type = this.type
        super.onBindViewHolder(viewHolder, position)
    }
}
