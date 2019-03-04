package com.shykun.volodymyr.movieviewer.presentation.tv.search

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.databinding.ItemSearchTvBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class TvSearchAdapter : BaseRecyclerViewAdapter<Tv, TvSearchViewHolder>() {

    private val clickSubject = PublishSubject.create<Int>()
    val clickObservable: Observable<Int> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvSearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSearchTvBinding>(
                inflater,
                R.layout.item_search_tv,
                parent,
                false)
        return TvSearchViewHolder(binding, clickSubject)
    }
}