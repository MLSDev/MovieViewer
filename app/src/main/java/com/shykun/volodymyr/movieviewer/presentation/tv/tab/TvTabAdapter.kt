package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalTvBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class TvTabAdapter(items: ArrayList<Tv>): BaseRecyclerViewAdapter<Tv, TvTabViewHolder>(items) {

    private val tvClickSubject = PublishSubject.create<Int>()

    val tvClickEvent: Observable<Int> = tvClickSubject
    var type: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvTabViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHorizontalTvBinding>(
                inflater,
                R.layout.item_horizontal__tv,
                parent,
                false)

        return TvTabViewHolder(binding, tvClickSubject)
    }

    override fun onBindViewHolder(viewHolder: TvTabViewHolder, position: Int) {
        viewHolder.type = this.type
        super.onBindViewHolder(viewHolder, position)
    }
}
