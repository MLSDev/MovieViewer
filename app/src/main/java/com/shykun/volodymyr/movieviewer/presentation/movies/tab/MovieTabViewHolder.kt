package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_holder_horizontal_item.view.*

class MovieTabViewHolder(itemView: View)
    : BaseViewHolder<Movie>(itemView) {

    var type = -1

    private val poster: ImageView = itemView.itemImage
    private val footerText: TextView = itemView.itemFooterText
    private val footerIcon: ImageView = itemView.itemFooterIcon


    override fun bind(item: Movie, position: Int) {
        super.bind(item, position)

        if (type != TOP_RATED_MOVIES)
            footerIcon.visibility = View.GONE

        footerText.text = when(type) {
            POPULAR_MOVIES -> "#${position+1}"
            TOP_RATED_MOVIES -> item.voteAverage.toString()
            UPCOMING_MOVIES -> item.releaseDate
            else -> ""

        }
        GlideApp.with(itemView)
                .load("http://image.tmdb.org/t/p/w185${item.posterPath}")
                .into(poster)
    }
}