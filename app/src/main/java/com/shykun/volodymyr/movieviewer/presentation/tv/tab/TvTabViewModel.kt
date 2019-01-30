package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.domain.TvUseCase

class TvTabViewModel(
        private val TVUseCase: TvUseCase) : ViewModel() {

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
        TVUseCase.execute(TvType.POPULAR, page)
                .doOnSuccess {
                    popularTvMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }

    fun getTopRatedTV(page: Int) {
        TVUseCase.execute(TvType.TOP_RATED, page)
                .doOnSuccess {
                    topRatedTvMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }

    fun getTVOnTheAir(page: Int) {
        TVUseCase.execute(TvType.ON_THE_AIR, page)
                .doOnSuccess {
                    tvOnTheAirMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }
}