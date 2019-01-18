package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderHorizontalMovieListBinding
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderHorizontalTvListBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.GeneralMovieTabViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class GeneralTvTabAdapter(items: ArrayList<ArrayList<Tv>>)
    : BaseRecyclerViewAdapter<ArrayList<Tv>, GeneralTvTabViewHolder>(items) {

    private val seeAllClickSubject = PublishSubject.create<Int>()
    private val tvClickSubject = PublishSubject.create<Int>()

    val seeAllClickEvent: Observable<Int> = seeAllClickSubject
    val tvClickEvent: Observable<Int> = tvClickSubject

    init {
        items.add(ArrayList())
        items.add(ArrayList())
        items.add(ArrayList()) //TODO: убрать этот костыль
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralTvTabViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewHolderHorizontalTvListBinding>(
                inflater,
                R.layout.view_holder_horizontal_tv_list,
                parent,
                false)

        return GeneralTvTabViewHolder(binding, seeAllClickSubject, tvClickSubject)
    }

    fun addTV(tvList: List<Tv>, position: Int) {
        items[position].addAll(tvList)
        notifyDataSetChanged()
    }
}