package com.shykun.volodymyr.movieviewer.presentation.movies

import com.arellomobile.mvp.MvpView
import com.shykun.volodymyr.movieviewer.data.entity.Movie

interface MoviesView : MvpView {
    fun showMovies(movies: ArrayList<Movie>)
    fun showError()
}