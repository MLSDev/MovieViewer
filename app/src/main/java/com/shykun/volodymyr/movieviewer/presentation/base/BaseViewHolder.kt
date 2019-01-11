package com.shykun.volodymyr.movieviewer.presentation.base

import android.support.v7.widget.RecyclerView
import android.view.View
import io.reactivex.subjects.PublishSubject

open class BaseViewHolder<T: Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var item: T

    open fun bind(item: T, position: Int) {
        this.item = item
    }
}