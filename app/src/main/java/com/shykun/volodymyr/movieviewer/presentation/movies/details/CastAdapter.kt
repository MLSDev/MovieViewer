package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderHorizontalItemActorBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter

class CastAdapter(private val cast: ArrayList<Actor>)
    : BaseRecyclerViewAdapter<Actor, CastViewHolder>(cast) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewHolderHorizontalItemActorBinding>(
                inflater,
                R.layout.view_holder_horizontal_item_actor,
                parent,
                false)

        return CastViewHolder(binding)
    }

    fun addCast(actors: List<Actor>) {
        this.cast.addAll(actors)
        notifyDataSetChanged()
    }
}