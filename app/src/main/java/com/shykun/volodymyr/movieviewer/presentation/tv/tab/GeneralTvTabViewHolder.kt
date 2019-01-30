package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalTvListBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_horizontal_tv_list.view.*
import java.util.*

class GeneralTvTabViewHolder(
        private val binding: ItemHorizontalTvListBinding,
        private val seeAllClickSubject: PublishSubject<Int>,
        private val tvClickSubject: PublishSubject<Int>)
    : BaseViewHolder<ArrayList<Tv>>(binding) {

    private val tvList: RecyclerView = itemView.horizontalTvList
    private val seeAllTv: TextView = itemView.seeAllTv

    lateinit var title: String
    var progressBarVisibility = View.VISIBLE

    override fun bind(item: ArrayList<Tv>, position: Int) {
        super.bind(item, position)

        title = when (position) {
            POPULAR_TV -> itemView.context.getString(R.string.popular_tv)
            TOP_RATED_TV -> itemView.context.getString(R.string.top_rated_tv)
            TV_ON_THE_AIR -> itemView.context.getString(R.string.tv_on_the_air)
            else -> ""
        }

        if (item.isNotEmpty()) {
            progressBarVisibility = View.GONE

            tvList.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayout.HORIZONTAL, false)
                val tvAdapter = TvTabAdapter(item)
                tvAdapter.clickObservable.subscribe { tvClickSubject.onNext(it) }
                tvAdapter.type = position
                adapter = tvAdapter
            }
        }

        seeAllTv.setOnClickListener { seeAllClickSubject.onNext(position) }

        executeBinding()
    }

    private fun executeBinding() {
        binding.generalTvTabViewHolder = this
        binding.executePendingBindings()
    }
}