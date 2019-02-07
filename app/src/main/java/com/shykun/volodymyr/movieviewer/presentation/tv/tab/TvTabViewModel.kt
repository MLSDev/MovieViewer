package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.data.network.response.TvResponse
import com.shykun.volodymyr.movieviewer.domain.TvUseCase

class TvTabViewModel(
        private val TVUseCase: TvUseCase) : ViewModel() {

    private val popularTvMutableLiveData = MutableLiveData<TvResponse>()
    private val topRatedTvMutableLiveData = MutableLiveData<TvResponse>()
    private val tvOnTheAirMutableLiveData = MutableLiveData<TvResponse>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val popularTvLiveData: LiveData<TvResponse> = popularTvMutableLiveData
    val topRatedTvLiveData: LiveData<TvResponse> = topRatedTvMutableLiveData
    val tvOnTheAirLiveData: LiveData<TvResponse> = tvOnTheAirMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData


    fun onViewLoaded() {
        getPopularTV(1)
        getTopRatedTV(1)
        getTVOnTheAir(1)
    }

    fun getPopularTV(page: Int) {
        TVUseCase.getTv(TvType.POPULAR, page)
                .subscribe(
                        { response -> popularTvMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    fun getTopRatedTV(page: Int) {
        TVUseCase.getTv(TvType.TOP_RATED, page)
                .subscribe(
                        { response -> topRatedTvMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    fun getTVOnTheAir(page: Int) {
        TVUseCase.getTv(TvType.ON_THE_AIR, page)
                .subscribe(
                        { response -> tvOnTheAirMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }
}