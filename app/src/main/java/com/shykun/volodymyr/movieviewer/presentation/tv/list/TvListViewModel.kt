package com.shykun.volodymyr.movieviewer.presentation.tv.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.data.network.response.TvResponse
import com.shykun.volodymyr.movieviewer.domain.TvUseCase

class TvListViewModel(private val tvUseCase: TvUseCase) : ViewModel() {

    private val tvListMutableLiveData = MutableLiveData<TvResponse>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val tvListLiveData: LiveData<TvResponse> = tvListMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun getTvList(page: Int, tvType: TvType) = tvUseCase.getTv(tvType, page)
            .subscribe(
                    { response -> tvListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun searchTv(query: String, page: Int) = tvUseCase.searchTv(query, page)
            .subscribe(
                    { response -> tvListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}