package com.shykun.volodymyr.movieviewer.presentation.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter

class TvTabAdapter(items: ArrayList<Tv>): BaseRecyclerViewAdapter<Tv, TvTabViewHolder>(items) {

    var type: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvTabViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_horizontal_item, parent, false)

        return TvTabViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TvTabViewHolder, position: Int) {
        viewHolder.type = this.type
        super.onBindViewHolder(viewHolder, position)
    }
}
