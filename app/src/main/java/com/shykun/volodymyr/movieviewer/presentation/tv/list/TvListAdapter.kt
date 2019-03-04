package com.shykun.volodymyr.movieviewer.presentation.tv.list

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.databinding.ItemLoadingBinding
import com.shykun.volodymyr.movieviewer.databinding.ItemTvBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

private const val TV = 0
private const val LOADING = 1

class TvListAdapter(val tvType: TvType) : BaseRecyclerViewAdapter<Tv, BaseTvListViewHolder>() {

    var nextPage = 1
    private val clickSubject = PublishSubject.create<Int>()
    val clickObservable: Observable<Int> = clickSubject
    var totalItemsCount = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseTvListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TV) {
            val binding = DataBindingUtil.inflate<ItemTvBinding>(
                    inflater,
                    R.layout.item_tv,
                    parent,
                    false)
            TvListViewHolder(binding, tvType, clickSubject)
        } else {
            val binding = DataBindingUtil.inflate<ItemLoadingBinding>(
                    inflater,
                    R.layout.item_loading,
                    parent,
                    false)
            TvListLoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(viewHolder: BaseTvListViewHolder, position: Int) {
        if (getItemViewType(position) == TV)
            super.onBindViewHolder(viewHolder, position)
        else
            (viewHolder as TvListLoadingViewHolder).bind(null, totalItemsCount)
    }

    override fun getItemCount() = items.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position < items.size)
            TV
        else
            LOADING
    }
}