package com.shykun.volodymyr.movieviewer.presentation.tv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.TV
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_holder_horizontal_item.view.*

class TVViewHolder(itemView: View, clickSubject: PublishSubject<TV>)
    : BaseViewHolder<TV>(itemView, clickSubject) {

    var type = -1

    val poster: ImageView = itemView.itemImage
    val footerText: TextView = itemView.itemFooterText
    val footerIcon: ImageView = itemView.itemFooterIcon


    override fun bind(item: TV, position: Int) {
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