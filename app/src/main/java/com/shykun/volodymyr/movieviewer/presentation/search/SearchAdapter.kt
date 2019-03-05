package com.shykun.volodymyr.movieviewer.presentation.search

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.databinding.ItemSearchBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter
import com.shykun.volodymyr.movieviewer.presentation.model.SearchListItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class SearchAdapter : BaseRecyclerViewAdapter<SearchListItem, SearchViewHolder>() {

    private val clickSubject = PublishSubject.create<Int>()
    val clickObservable: Observable<Int> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSearchBinding>(
                inflater,
                R.layout.item_search,
                parent,
                false)

        return SearchViewHolder(binding, clickSubject)
    }
}