package com.shykun.volodymyr.movieviewer.presentation.common

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

open class BaseViewHolder<T: Any>(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    var item: T? = null

    open fun bind(item: T?, totalItemsCount: Int) {
        this.item = item
    }
}