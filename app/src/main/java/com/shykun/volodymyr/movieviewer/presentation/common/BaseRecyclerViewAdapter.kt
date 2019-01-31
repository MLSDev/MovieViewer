package com.shykun.volodymyr.movieviewer.presentation.common

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import java.util.*

abstract class BaseRecyclerViewAdapter<T: Any, VH: BaseViewHolder<T>> (protected var items: ArrayList<T>) : RecyclerView.Adapter<VH>() {

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        val item = items[position]
        viewHolder.bind(item, position)
    }
}