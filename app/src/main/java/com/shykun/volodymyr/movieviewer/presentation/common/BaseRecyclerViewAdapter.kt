package com.shykun.volodymyr.movieviewer.presentation.common

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class BaseRecyclerViewAdapter<T : Any, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    protected val items: ArrayList<T> = ArrayList()

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        val item = if (position < items.size) items[position] else null
        viewHolder.bind(item)
    }

    fun addItems(items: List<T>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setItems(items: List<T>) {
        clearItems()
        addItems(items)
    }

    fun clearItems() {
        this.items.clear()
    }
}