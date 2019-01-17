package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.Actor
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp
import kotlinx.android.synthetic.main.view_holder_horizontal_item.view.*

class CastViewHolder(itemView: View) : BaseViewHolder<Actor>(itemView) {

    val photo: ImageView = itemView.itemImage
    val name: TextView = itemView.itemFooterText
    val icon: ImageView = itemView.itemFooterIcon

    override fun bind(item: Actor, position: Int) {
        super.bind(item, position)

        name.text = item.name

        GlideApp.with(itemView)
                .load("http://image.tmdb.org/t/p/w185${item.profilePath}")
                .into(photo)
    }
}