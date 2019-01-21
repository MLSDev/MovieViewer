package com.shykun.volodymyr.movieviewer.presentation.tv.details

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderRecommendedTvItemBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter

class RecommendedTvAdapter(private val tvList: ArrayList<Tv>) : BaseRecyclerViewAdapter<Tv, RecommendedTvViewHolder>(tvList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedTvViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewHolderRecommendedTvItemBinding>(
                inflater,
                R.layout.view_holder_recommended_tv_item,
                parent,
                false)

        return RecommendedTvViewHolder(binding)
    }

    fun addTvList(tvList: List<Tv>) {
        this.tvList.addAll(tvList)
        notifyDataSetChanged()
    }
}