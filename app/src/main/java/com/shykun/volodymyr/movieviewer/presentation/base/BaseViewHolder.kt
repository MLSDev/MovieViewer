package com.shykun.volodymyr.movieviewer.presentation.base

import android.support.v7.widget.RecyclerView
import android.view.View
import io.reactivex.subjects.PublishSubject

abstract class BaseViewHolder<T: Any>(itemView: View, clickSubject: PublishSubject<T>) : RecyclerView.ViewHolder(itemView) {

    private lateinit var item: T

    init {
        itemView.setOnClickListener {
            clickSubject.onNext(item)
        }
    }

    open fun bind(item: T) {
        this.item = item
    }
}