package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_holder_horizontal_list.view.*

class GeneralTvTabViewHolder(itemView: View, private val clickSubject: PublishSubject<Int>)
    : BaseViewHolder<ArrayList<Tv>>(itemView) {

    private val title: TextView = itemView.horizontalListTitle
    private val list: RecyclerView = itemView.horizontalList
    private val progressBar: ProgressBar = itemView.horizontalListProgressBar
    private val seeAll: TextView = itemView.seeAll

    override fun bind(item: ArrayList<Tv>, position: Int) {
        super.bind(item, position)

        if (item.isEmpty()) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
            title.text = when (position) {
                POPULAR_TV -> "Popular TV"
                TOP_RATED_TV -> "Top rated TV"
                TV_ON_THE_AIR -> "TV on the air"
                else -> ""
            }

            list.apply {
                layoutManager = LinearLayoutManager(horizontalList.context, LinearLayout.HORIZONTAL, false)
                val tvAdapter = TvTabAdapter(item)
                tvAdapter.type = position
                adapter = tvAdapter
            }
        }

        seeAll.setOnClickListener { clickSubject.onNext(position) }
    }
}