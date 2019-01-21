package com.shykun.volodymyr.movieviewer.presentation.tv.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.domain.TvUseCase

class TvListViewModel(private val tvUseCase: TvUseCase) : ViewModel() {

    lateinit var tvType: TvType

    private val tvListMutableLiveData = MutableLiveData<List<Tv>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val tvListLiveData: LiveData<List<Tv>> = tvListMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun onViewLoaded(tvType: TvType) {
        this.tvType = tvType
        getTvList(1)
    }

    fun getTvList(page: Int) = tvUseCase.execute(tvType, page)
            .doOnSuccess {
                tvListMutableLiveData.value = it
            }
            .doOnError {
                loadingErrorMutableLiveData.value = it.message
            }
            .subscribe()
}