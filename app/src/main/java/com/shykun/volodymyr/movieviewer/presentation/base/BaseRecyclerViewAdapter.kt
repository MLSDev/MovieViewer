package com.shykun.volodymyr.movieviewer.presentation.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

abstract class BaseRecyclerViewAdapter<T: Any, VH: BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    private lateinit var items: ArrayList<T>
    private lateinit var clickSubject: PublishSubject<T>
    val clickEvent: Observable<T> = clickSubject

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        val item = items[position]
        viewHolder.bind(item)
    }

    fun setItems(items: ArrayList<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}