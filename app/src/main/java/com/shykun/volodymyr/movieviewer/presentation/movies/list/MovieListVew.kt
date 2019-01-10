package com.shykun.volodymyr.movieviewer.presentation.movies.list

import com.arellomobile.mvp.MvpView
import com.shykun.volodymyr.movieviewer.data.entity.Movie

interface MovieListVew : MvpView {
    fun showMovies(movieList: ArrayList<Movie>)
    fun showError()
}