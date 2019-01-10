package com.shykun.volodymyr.movieviewer.presentation.movies.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.domain.GetMoviesUseCase
import javax.inject.Inject

@InjectViewState
class MovieListPresenter @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) : MvpPresenter<MovieListVew>() {

    lateinit var moviesType: MoviesType

    fun onViewLoaded(moviesType: MoviesType) {
        this.moviesType = moviesType
    }

    fun getMovies(page: Int) = getMoviesUseCase.execute(moviesType, page)
            .doOnSuccess {
                viewState.showMovies(it)
            }
            .doOnError {
                viewState.showError()
            }
            .subscribe()
}