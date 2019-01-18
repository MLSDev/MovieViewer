package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp
import kotlinx.android.synthetic.main.view_holder_horizontal_item.view.*
import kotlinx.android.synthetic.main.view_holder_recommended_movies_item.view.*

class RecommendedMoviesViewHolder(itemView: View) : BaseViewHolder<Movie>(itemView) {

    private val rating: TextView = itemView.itemFooterText
    private val poster: ImageView = itemView.itemImage

    override fun bind(item: Movie, position: Int) {
        super.bind(item, position)

        rating.text = item.voteAverage.toString()

        GlideApp.with(itemView)
                .load("http://image.tmdb.org/t/p/w185${item.posterPath}")
                .into(poster)
    }
}