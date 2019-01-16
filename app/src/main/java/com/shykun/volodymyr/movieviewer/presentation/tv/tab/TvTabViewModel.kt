package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.domain.GetTvUseCase
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TV_DETAILS_FRAGMENT
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_LIST_FRAGMENT_KEY
import ru.terrakok.cicerone.Router

class TvTabViewModel(
        private val getTVUseCase: GetTvUseCase,
        private val router: Router) : ViewModel() {

    private val popularTvMutableLiveData = MutableLiveData<List<Tv>>()
    private val topRatedTvMutableLiveData = MutableLiveData<List<Tv>>()
    private val tvOnTheAirMutableLiveData = MutableLiveData<List<Tv>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val popularTvLiveData: LiveData<List<Tv>> = popularTvMutableLiveData
    val topRatedTvLiveData: LiveData<List<Tv>> = topRatedTvMutableLiveData
    val tvOnTheAirLiveData: LiveData<List<Tv>> = tvOnTheAirMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData


    fun onViewLoaded() {
        getPopularTV(1)
        getTopRatedTV(1)
        getTVOnTheAir(1)
    }

    fun getPopularTV(page: Int) {
        getTVUseCase.execute(TvType.POPULAR, page)
                .doOnSuccess {
                    popularTvMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }

    fun getTopRatedTV(page: Int) {
        getTVUseCase.execute(TvType.TOP_RATED, page)
                .doOnSuccess {
                    topRatedTvMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }

    fun getTVOnTheAir(page: Int) {
        getTVUseCase.execute(TvType.ON_THE_AIR, page)
                .doOnSuccess {
                    tvOnTheAirMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }

    fun onViewAllButtonClicked(tvType: TvType) {
        router.navigateTo(TV_LIST_FRAGMENT_KEY, tvType)
    }

    fun onTvClicked(tvId: Int) {
        router.navigateTo(TV_DETAILS_FRAGMENT)
    }
}