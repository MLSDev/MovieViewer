package com.shykun.volodymyr.movieviewer.presentation.tv.details

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.databinding.ItemRecommendedTvBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter

class RecommendedTvAdapter(private val tvList: ArrayList<Tv>) : BaseRecyclerViewAdapter<Tv, RecommendedTvViewHolder>(tvList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedTvViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemRecommendedTvBinding>(
                inflater,
                R.layout.item_recommended_tv,
                parent,
                false)

        return RecommendedTvViewHolder(binding)
    }

    fun addTvList(tvList: List<Tv>) {
        this.tvList.addAll(tvList)
        notifyDataSetChanged()
    }
}