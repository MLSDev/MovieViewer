package com.shykun.volodymyr.movieviewer.presentation.tv

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.domain.GetTVUseCase
import javax.inject.Inject

@InjectViewState
class TvTabPresenter @Inject constructor(private val getTVUseCase: GetTVUseCase) : MvpPresenter<TvTabView>() {

    fun onViewLoaded() {
        getPopularTV()
        getTopRatedTV()
        getTVOnTheAir()
    }

    fun getPopularTV() {
        getTVUseCase.execute(TvType.POPULAR)
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
        getTVUseCase.execute(TvType.TOP_RATED)
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
        getTVUseCase.execute(TvType.ON_THE_AIR)
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