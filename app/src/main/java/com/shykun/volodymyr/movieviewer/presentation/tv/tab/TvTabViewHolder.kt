package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp
import kotlinx.android.synthetic.main.view_holder_horizontal_item.view.*

class TvTabViewHolder(itemView: View)
    : BaseViewHolder<Tv>(itemView) {

    var type = -1

    private val poster: ImageView = itemView.itemImage
    private val footerText: TextView = itemView.itemFooterText
    private val footerIcon: ImageView = itemView.itemFooterIcon


    override fun bind(item: Tv, position: Int) {
        super.bind(item, position)

        if (type == POPULAR_TV)
            footerIcon.visibility = View.GONE

        footerText.text = when(type) {
            POPULAR_TV -> "#${position+1}"
            TOP_RATED_TV -> item.voteAverage.toString()
            TV_ON_THE_AIR -> item.voteAverage.toString()
            else -> ""

        }
        GlideApp.with(itemView)
                .load("http://image.tmdb.org/t/p/w185${item.posterPath}")
                .into(poster)
    }
}