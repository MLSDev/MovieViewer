package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.domain.GetMoviesUseCase
import com.shykun.volodymyr.movieviewer.presentation.App
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_LIST_FRAGMENT_KEY
import javax.inject.Inject

@InjectViewState
class MovieTabPresenter @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) : MvpPresenter<MovieTabView>() {

    fun onViewLoaded() {
        getPopularMovies(1)
        getTopRatedMovies(1)
        getUpcomingMovies(1)
    }

    fun getPopularMovies(page: Int) {
        getMoviesUseCase.execute(MoviesType.POPULAR, page)
                .doOnSuccess {
                    viewState.showPopularMovies(it)
                }
                .doOnError {
                    viewState.showError()
                    Log.d("getMovieError", it.message)
                }
                .subscribe()
    }

    fun getTopRatedMovies(page: Int) {
        getMoviesUseCase.execute(MoviesType.TOP_RATED, page)
                .doOnSuccess {
                    viewState.showTopRatedMovies(it)
                }
                .doOnError {
                    viewState.showError()
                    Log.d("getMovieError", it.message)
                }
                .subscribe()
    }

    fun getUpcomingMovies(page: Int) {
        getMoviesUseCase.execute(MoviesType.UPCOMING, page)
                .doOnSuccess {
                    viewState.showUpcompingMovies(it)
                }
                .doOnError {
                    viewState.showError()
                    Log.d("getMovieError", it.message)
                }
                .subscribe()
    }

    fun onViewAllButtonClicked(moviesType: MoviesType) {
        App.instance.router.navigateTo(MOVIE_LIST_FRAGMENT_KEY, moviesType)
    }

}