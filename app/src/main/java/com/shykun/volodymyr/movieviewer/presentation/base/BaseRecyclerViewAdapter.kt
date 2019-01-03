package com.shykun.volodymyr.movieviewer.presentation.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

abstract class BaseRecyclerViewAdapter<T: Any, VH: BaseViewHolder<T>> (protected var items: ArrayList<T>) : RecyclerView.Adapter<VH>() {

//    protected lateinit var items: ArrayList<T>
    val clickSubject: PublishSubject<T> = PublishSubject.create()
    val clickEvent: Observable<T> = clickSubject

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        val item = items[position]
        viewHolder.bind(item, position)
    }
}