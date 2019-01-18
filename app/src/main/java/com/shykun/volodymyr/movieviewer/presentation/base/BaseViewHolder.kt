package com.shykun.volodymyr.movieviewer.presentation.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import io.reactivex.subjects.PublishSubject

open class BaseViewHolder<T: Any>(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    private lateinit var item: T

    open fun bind(item: T, position: Int) {
        this.item = item
    }
}