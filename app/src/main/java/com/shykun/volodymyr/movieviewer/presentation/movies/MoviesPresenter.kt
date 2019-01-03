package com.shykun.volodymyr.movieviewer.presentation.movies

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.domain.GetMoviesUseCase
import javax.inject.Inject

@InjectViewState
class MoviesPresenter @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) : MvpPresenter<MoviesView>() {

    fun onViewLoaded() {
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    fun getPopularMovies() {
        getMoviesUseCase.getMovies(MoviesType.POPULAR)
                .doOnSuccess {
                    viewState.showPopularMovies(it)
                }
                .doOnError {
                    viewState.showError()
                }
                .doOnError {
                    viewState.showError()
                    Log.d("getMovieError", it.message)
                }
                .subscribe()
    }

    fun getTopRatedMovies() {
        getMoviesUseCase.getMovies(MoviesType.TOP_RATED)
                .doOnSuccess {
                    viewState.showTopRatedMovies(it)
                }
                .doOnError {
                    viewState.showError()
                    Log.d("getMovieError", it.message)
                }
                .subscribe()
    }

    fun getUpcomingMovies() {
        getMoviesUseCase.getMovies(MoviesType.UPCOMING)
                .doOnSuccess {
                    viewState.showUpcompingMovies(it)
                }
                .doOnError {
                    viewState.showError()
                    Log.d("getMovieError", it.message)
                }
                .subscribe()
    }

}