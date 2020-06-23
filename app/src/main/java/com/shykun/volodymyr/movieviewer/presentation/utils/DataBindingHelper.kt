package com.shykun.volodymyr.movieviewer.presentation.utils

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.view.View
import android.widget.ImageView
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.*
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp

@BindingAdapter("url")
fun loadImage(imageView: ImageView, url: String?) {
    if (url != null) {
        GlideApp.with(imageView)
                .load("http://image.tmdb.org/t/p/w500$url")
                .into(imageView)
    } else {
        imageView.setImageResource(R.drawable.ic_person)
    }
}

@BindingAdapter("icon")
fun setImageViewResource(imageView: ImageView, resourceId: Int?) {
    if (resourceId != null)
        imageView.setImageResource(resourceId)
    else
        imageView.visibility = View.GONE
}

@BindingConversion
fun genresToString(genres: ArrayList<Genre>?): String? = genres?.joinToString { it.name }

@BindingConversion
fun countriesToString(countries: List<Country>?): String? = countries?.joinToString { it.name }

@BindingConversion
fun getGenres(genreIds: List<Int>?): String {
    if (genreIds == null)
        return ""
    val genres = ArrayList<String>()
    for (id in genreIds)
        genres.add(GenreHelper.genres[id]!!)
    return genres.joinToString()
}

@BindingConversion
fun doubleToString(value: Double): String {
    return value.toString()
}

@BindingConversion
fun listToString(value: List<String>?) = value?.joinToString()
