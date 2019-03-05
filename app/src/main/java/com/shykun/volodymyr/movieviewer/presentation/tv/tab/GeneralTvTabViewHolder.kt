package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalTvListBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.common.adapters.HorizontalListAdapter
import com.shykun.volodymyr.movieviewer.presentation.utils.popularTvToHorizontalListItem
import com.shykun.volodymyr.movieviewer.presentation.utils.topRatedTvToHorizontalListItem
import com.shykun.volodymyr.movieviewer.presentation.utils.tvOnTheAirToHorizontalListItem
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
    lateinit var tvType: TvType
    var progressBarVisibility = View.VISIBLE

    override fun bind(item: ArrayList<Tv>?) {
        super.bind(item)

        when (adapterPosition) {
            POPULAR_TV -> {
                title = itemView.context.getString(R.string.popular_tv)
                tvType = TvType.POPULAR
            }
            TOP_RATED_TV -> {
                title = itemView.context.getString(R.string.top_rated_tv)
                tvType = TvType.TOP_RATED
            }
            TV_ON_THE_AIR -> {
                title = itemView.context.getString(R.string.tv_on_the_air)
                tvType = TvType.ON_THE_AIR
            }
        }

        if (item != null && item.isNotEmpty()) {
            progressBarVisibility = View.GONE

            tvList.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayout.HORIZONTAL, false)
                val items = when (tvType) {
                    TvType.TOP_RATED -> item.map { topRatedTvToHorizontalListItem(it) }
                    TvType.POPULAR -> item.mapIndexed { position, tv -> popularTvToHorizontalListItem(tv, position) }
                    TvType.ON_THE_AIR -> item.map { tvOnTheAirToHorizontalListItem(it) }
                    else -> null
                }
                val tvAdapter = HorizontalListAdapter()
                tvAdapter.addItems(items!!)
                tvAdapter.clickObservable.subscribe { tvClickSubject.onNext(it.id) }
                adapter = tvAdapter
            }
        }

        seeAllTv.setOnClickListener { seeAllClickSubject.onNext(adapterPosition) }

        executeBinding()
    }

    private fun executeBinding() {
        binding.generalTvTabViewHolder = this
        binding.executePendingBindings()
    }
}