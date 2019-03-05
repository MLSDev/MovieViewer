package com.shykun.volodymyr.movieviewer.presentation.common.adapters

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalListBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalListItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class HorizontalListAdapter : BaseRecyclerViewAdapter<HorizontalListItem, HorizontalListViewHolder>() {

    private val clickSubject = PublishSubject.create<HorizontalListItem>()
    val clickObservable: Observable<HorizontalListItem> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHorizontalListBinding>(
                inflater, R.layout.item_horizontal_list, parent, false)
        return HorizontalListViewHolder(binding, clickSubject)
    }
}