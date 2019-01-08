package com.shykun.volodymyr.movieviewer.presentation.tv

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

class GeneralTvTabViewHolder(itemView: View, clickSubject: PublishSubject<ArrayList<Tv>>)
    : BaseViewHolder<ArrayList<Tv>>(itemView, clickSubject) {

    val title: TextView = itemView.horizontalListTitle
    val list: RecyclerView = itemView.horizontalList
    val progressBar: ProgressBar = itemView.horizontalListProgressBar

    override fun bind(item: ArrayList<Tv>, position: Int) {
        super.bind(item, position)

        if (item.isEmpty()) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
            title.text = when (position) {
                POPULAR_TV -> "Popular Tv"
                TOP_RATED_TV -> "Top rated Tv"
                TV_ON_THE_AIR -> "Tv on the air"
                else -> ""
            }
            list.apply {
                layoutManager = LinearLayoutManager(horizontalList.context, LinearLayout.HORIZONTAL, false)
                val tvAdapter = TvTabAdapter(item)
                tvAdapter.type = position
                adapter = tvAdapter

            }
        }
    }
}