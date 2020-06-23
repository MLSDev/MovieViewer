package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalTvListBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class GeneralTvTabAdapter : BaseRecyclerViewAdapter<ArrayList<HorizontalItem>, GeneralTvTabViewHolder>() {

    private val seeAllClickSubject = PublishSubject.create<Int>()
    private val tvClickSubject = PublishSubject.create<Int>()

    val seeAllClickEvent: Observable<Int> = seeAllClickSubject
    val tvClickEvent: Observable<Int> = tvClickSubject

    init {
        items.add(ArrayList())
        items.add(ArrayList())
        items.add(ArrayList()) //TODO: Remove this workaround
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralTvTabViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHorizontalTvListBinding>(
                inflater,
                R.layout.item_horizontal_tv_list,
                parent,
                false)

        return GeneralTvTabViewHolder(binding, seeAllClickSubject, tvClickSubject)
    }

    fun addTV(tvList: List<HorizontalItem>, position: Int) {
        items[position].addAll(tvList)
        notifyDataSetChanged()
    }
}