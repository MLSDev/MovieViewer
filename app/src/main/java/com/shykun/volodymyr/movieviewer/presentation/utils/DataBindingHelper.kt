package com.shykun.volodymyr.movieviewer.presentation.utils

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.widget.ImageView
import com.shykun.volodymyr.movieviewer.data.entity.Country
import com.shykun.volodymyr.movieviewer.data.entity.Genre
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