package com.shykun.volodymyr.movieviewer.presentation.movies

import android.view.View
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class MovieViewHolder(itemView: View, clickSubject: PublishSubject<Movie>)
    : BaseViewHolder<Movie>(itemView, clickSubject) {

    override fun bind(item: Movie) {
        super.bind(item)
    }
}