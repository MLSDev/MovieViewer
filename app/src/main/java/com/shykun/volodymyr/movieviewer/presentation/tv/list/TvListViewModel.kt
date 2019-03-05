package com.shykun.volodymyr.movieviewer.presentation.tv.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.data.network.response.TvResponse
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import com.shykun.volodymyr.movieviewer.domain.TvUseCase

class TvListViewModel(private val tvUseCase: TvUseCase,
                      private val profileUseCase: ProfileUseCase,
                      private val searchUseCase: SearchUseCase) : ViewModel() {

    private val tvListMutableLiveData = MutableLiveData<TvResponse>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val tvListLiveData: LiveData<TvResponse> = tvListMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData

    fun getTvList(tvType: TvType, page: Int) = tvUseCase.getTv(tvType, page)
            .subscribe(
                    { response -> tvListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun searchTv(query: String, page: Int) = searchUseCase.searchTv(query, page)
            .subscribe(
                    { response -> tvListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getRatedTv(sessionId: String, page: Int) = profileUseCase.getRatedTv(sessionId, page)
            .subscribe(
                    { response -> tvListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getTvWatchlist(sessionId: String, page: Int) = profileUseCase.getTvWatchList(sessionId, page)
            .subscribe(
                    { response -> tvListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )

    fun getFavoriteTv(sessionId: String, page: Int) = profileUseCase.getFavoriteTv(sessionId, page)
            .subscribe(
                    { response -> tvListMutableLiveData.value = response },
                    { error -> loadingErrorMutableLiveData.value = error.message }
            )
}