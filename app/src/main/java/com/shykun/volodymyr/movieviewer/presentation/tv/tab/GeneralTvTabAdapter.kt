package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class GeneralTvTabAdapter(items: ArrayList<ArrayList<Tv>>)
    : BaseRecyclerViewAdapter<ArrayList<Tv>, GeneralTvTabViewHolder>(items) {

    private val clickSubject = PublishSubject.create<Int>()
    val clickEvent : Observable<Int> = clickSubject

    init {
        items.add(ArrayList())
        items.add(ArrayList())
        items.add(ArrayList()) //TODO: убрать этот костыль
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralTvTabViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_horizontal_list, parent, false)

        return GeneralTvTabViewHolder(view, clickSubject)
    }

    fun addTV(tvList: ArrayList<Tv>, position: Int) {
        items[position].addAll(tvList)
        notifyDataSetChanged()
    }
}