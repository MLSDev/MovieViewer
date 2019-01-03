package com.shykun.volodymyr.movieviewer.presentation.movies

import com.arellomobile.mvp.MvpView
import com.shykun.volodymyr.movieviewer.data.entity.Movie

interface MoviesView : MvpView {
    fun showPopularMovies(movies: ArrayList<Movie>)
    fun showTopRatedMovies(movies: ArrayList<Movie>)
    fun showUpcompingMovies(movies: ArrayList<Movie>)

    fun showError()
}