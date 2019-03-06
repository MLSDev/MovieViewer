package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.domain.TvUseCase
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalItem
import com.shykun.volodymyr.movieviewer.presentation.utils.ioMainSubscribe
import com.shykun.volodymyr.movieviewer.presentation.utils.popularTvToHorizontalListItem
import com.shykun.volodymyr.movieviewer.presentation.utils.topRatedTvToHorizontalListItem
import com.shykun.volodymyr.movieviewer.presentation.utils.tvOnTheAirToHorizontalListItem

class TvTabViewModel(
        private val TVUseCase: TvUseCase) : ViewModel() {

    private val popularTvMutableLiveData = MutableLiveData<List<HorizontalItem>>()
    private val topRatedTvMutableLiveData = MutableLiveData<List<HorizontalItem>>()
    private val tvOnTheAirMutableLiveData = MutableLiveData<List<HorizontalItem>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val popularTvLiveData: LiveData<List<HorizontalItem>> = popularTvMutableLiveData
    val topRatedTvLiveData: LiveData<List<HorizontalItem>> = topRatedTvMutableLiveData
    val tvOnTheAirLiveData: LiveData<List<HorizontalItem>> = tvOnTheAirMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData


    fun onViewLoaded() {
        getPopularTV(1)
        getTopRatedTV(1)
        getTVOnTheAir(1)
    }

    fun getPopularTV(page: Int) {
        TVUseCase.getTv(TvType.POPULAR, page)
                .map { it.results.mapIndexed { position, tv -> popularTvToHorizontalListItem(tv, position) } }
                .ioMainSubscribe(
                        { response -> popularTvMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    fun getTopRatedTV(page: Int) {
        TVUseCase.getTv(TvType.TOP_RATED, page)
                .map { it.results.map { topRatedTvToHorizontalListItem(it) } }
                .ioMainSubscribe(
                        { response -> topRatedTvMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    fun getTVOnTheAir(page: Int) {
        TVUseCase.getTv(TvType.ON_THE_AIR, page)
                .map { it.results.map { tvOnTheAirToHorizontalListItem(it) } }
                .ioMainSubscribe(
                        { response -> tvOnTheAirMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }
}