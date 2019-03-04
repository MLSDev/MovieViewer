package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalActorBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CastAdapter : BaseRecyclerViewAdapter<Actor, CastViewHolder>() {

    private val clickSubject = PublishSubject.create<Int>()
    val clickObservable: Observable<Int> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHorizontalActorBinding>(
                inflater,
                R.layout.item_horizontal_actor,
                parent,
                false)

        return CastViewHolder(binding, clickSubject)
    }
}