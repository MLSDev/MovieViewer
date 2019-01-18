package com.shykun.volodymyr.movieviewer.presentation.utils

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.widget.ImageView
import com.shykun.volodymyr.movieviewer.data.entity.*
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp

@BindingAdapter("url")
fun loadImage(imageView: ImageView, url: String?) {
    if (url != null) {
        GlideApp.with(imageView)
                .load("http://image.tmdb.org/t/p/w185$url")
                .into(imageView)
    }
}

@BindingConversion
fun genresToString(genres: List<Genre>?) = genres?.joinToString { it.name }

@BindingConversion
fun countriesToString(countries: List<Country>?) = countries?.joinToString { it.name }

@BindingConversion
fun getGenres(genreIds: List<Int>): String {
    val genres = ArrayList<String>()
    for (id in genreIds)
        genres.add(GenreHelper.genres[id]!!)
    return genres.joinToString()
}