package com.shykun.volodymyr.movieviewer.presentation.common.adapters

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.databinding.ItemLoadingBinding
import com.shykun.volodymyr.movieviewer.databinding.ItemVerticalBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter
import com.shykun.volodymyr.movieviewer.presentation.model.VerticalItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

private const val ITEM = 0
private const val LOADING = 1

class VerticalListAdapter : BaseRecyclerViewAdapter<VerticalItem, BaseVerticalListViewHolder>() {

    private val clickSubject = PublishSubject.create<Int>()
    val clickEvent: Observable<Int> = clickSubject
    var nextPage = 1
    var totalItemsCount = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVerticalListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == ITEM) {
            val binding = DataBindingUtil.inflate<ItemVerticalBinding>(
                    inflater,
                    R.layout.item_vertical,
                    parent,
                    false)
            VerticalListViewHolder(binding, clickSubject)
        } else {
            val binding = DataBindingUtil.inflate<ItemLoadingBinding>(
                    inflater,
                    R.layout.item_loading,
                    parent,
                    false)
            VerticalListLoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(viewHolder: BaseVerticalListViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM)
            super.onBindViewHolder(viewHolder, position)
        else
            (viewHolder as VerticalListLoadingViewHolder).bind(null, totalItemsCount)
    }

    override fun getItemCount() = items.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position < items.size)
            ITEM
        else
            LOADING
    }
}
