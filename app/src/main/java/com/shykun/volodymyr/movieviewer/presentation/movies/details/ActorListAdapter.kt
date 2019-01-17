package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter

class CastAdapter(private val cast: ArrayList<Actor>)
    : BaseRecyclerViewAdapter<Actor, CastViewHolder>(cast) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_horizontal_item, parent, false)

        return CastViewHolder(view)
    }

    fun addCast(actors: List<Actor>) {
        this.cast.addAll(actors)
        notifyDataSetChanged()
    }
}