package com.shykun.volodymyr.movieviewer.presentation.tv

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.network.response.TVType
import com.shykun.volodymyr.movieviewer.domain.GetMoviesUseCase
import com.shykun.volodymyr.movieviewer.domain.GetTVUseCase
import com.shykun.volodymyr.movieviewer.presentation.movies.MoviesView
import javax.inject.Inject

@InjectViewState
class TVPresenter @Inject constructor(private val getTVUseCase: GetTVUseCase) : MvpPresenter<TVView>() {

    fun onViewLoaded() {
        getPopularTV()
        getTopRatedTV()
        getTVOnTheAir()
    }

    fun getPopularTV() {
        getTVUseCase.getTV(TVType.POPULAR)
                .doOnSuccess {
                    viewState.showPopularTV(it)
                }
                .doOnError {
                    viewState.showError()
                    Log.d("getMovieError", it.message)
                }
                .subscribe()
    }

    fun getTopRatedTV() {
        getTVUseCase.getTV(TVType.TOP_RATED)
                .doOnSuccess {
                    viewState.showTopRatedTV(it)
                }
                .doOnError {
                    viewState.showError()
                    Log.d("getMovieError", it.message)
                }
                .subscribe()
    }

    fun getTVOnTheAir() {
        getTVUseCase.getTV(TVType.ON_THE_AIR)
                .doOnSuccess {
                    viewState.showTVOnTheAir(it)
                }
                .doOnError {
                    viewState.showError()
                    Log.d("getMovieError", it.message)
                }
                .subscribe()
    }

}