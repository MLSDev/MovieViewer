package com.shykun.volodymyr.movieviewer.presentation.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.TV
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter

class GeneralTVAdapter(items: ArrayList<ArrayList<TV>>)
    : BaseRecyclerViewAdapter<ArrayList<TV>, GeneralTVViewHolder>(items) {

    init {
        items.add(ArrayList())
        items.add(ArrayList())
        items.add(ArrayList()) //TODO: убрать этот костыль
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralTVViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_horizontal_list, parent, false)

        return GeneralTVViewHolder(view, clickSubject)
    }

    fun addTV(tvList: ArrayList<TV>, position: Int) {
        items[position].addAll(tvList)
        notifyDataSetChanged()
    }
}