package com.shykun.volodymyr.movieviewer.presentation.movies

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.domain.GetMoviesUseCase
import javax.inject.Inject

@InjectViewState
class MoviesPresenter @Inject constructor(
        private val getMoviesUseCase: GetMoviesUseCase) : MvpPresenter<MoviesView>() {

    fun getMovies(moviesType: MoviesType) {
        getMoviesUseCase.getMovies(moviesType)
                .doOnSuccess {
                    viewState.showMovies(it)
                }
                .doOnError {
                    viewState.showError()
                }
                .subscribe()
    }
}