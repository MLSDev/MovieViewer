package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.domain.GetTvUseCase
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_LIST_FRAGMENT_KEY
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class TvTabPresenter @Inject constructor(
        private val getTVUseCase: GetTvUseCase,
        private val router: Router) : MvpPresenter<TvTabView>() {

    fun onViewLoaded() {
        getPopularTV(1)
        getTopRatedTV(1)
        getTVOnTheAir(1)
    }

    fun getPopularTV(page: Int) {
        getTVUseCase.execute(TvType.POPULAR, page)
                .doOnSuccess {
                    viewState.showPopularTV(it)
                }
                .doOnError {
                    viewState.showError()
                    Log.d("getMovieError", it.message)
                }
                .subscribe()
    }

    fun getTopRatedTV(page: Int) {
        getTVUseCase.execute(TvType.TOP_RATED, page)
                .doOnSuccess {
                    viewState.showTopRatedTV(it)
                }
                .doOnError {
                    viewState.showError()
                    Log.d("getMovieError", it.message)
                }
                .subscribe()
    }

    fun getTVOnTheAir(page: Int) {
        getTVUseCase.execute(TvType.ON_THE_AIR, page)
                .doOnSuccess {
                    viewState.showTVOnTheAir(it)
                }
                .doOnError {
                    viewState.showError()
                    Log.d("getMovieError", it.message)
                }
                .subscribe()
    }

    fun onViewAllButtonClicked(tvType: TvType) {
        router.navigateTo(TV_LIST_FRAGMENT_KEY, tvType)
    }
}