package com.shykun.volodymyr.movieviewer.presentation.tv.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.GenreHelper
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp
import kotlinx.android.synthetic.main.view_holder_movie.view.*

open class BaseTvListViewHolder(itemView: View)
    : BaseViewHolder<Tv>(itemView)

class TvListLoadingViewHolder(itemView: View)
    : BaseTvListViewHolder(itemView)

class TvListViewHolder(itemView: View, val tvType: TvType)
    : BaseTvListViewHolder(itemView) {

    private val poster: ImageView = itemView.poster
    private val name: TextView = itemView.title
    private val rating: TextView = itemView.rating
    private val popularity: TextView = itemView.popularity
    private val genres: TextView = itemView.genres
    private val releaseDate: TextView = itemView.releaseDate

    override fun bind(item: Tv, position: Int) {
        super.bind(item, position)

        name.text = item.name
        rating.text = item.voteAverage.toString()
        genres.text = GenreHelper.getGenres(item)

        when (tvType) {
            TvType.TOP_RATED -> {
                popularity.visibility = View.GONE
                releaseDate.visibility = View.GONE
            }
            TvType.POPULAR -> {
                popularity.text = "#${position + 1}"
                releaseDate.visibility = View.GONE
            }
            TvType.ON_THE_AIR -> {
                releaseDate.text = item.firstAirDate
                popularity.visibility = View.GONE
            }
        }

        GlideApp.with(itemView)
                .load("http://image.tmdb.org/t/p/w185${item.posterPath}")
                .into(poster)
    }
}