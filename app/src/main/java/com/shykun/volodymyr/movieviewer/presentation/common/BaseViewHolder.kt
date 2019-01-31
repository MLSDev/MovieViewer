package com.shykun.volodymyr.movieviewer.presentation.common

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

open class BaseViewHolder<T: Any>(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    lateinit var item: T

    open fun bind(item: T, position: Int) {
        this.item = item
    }
}