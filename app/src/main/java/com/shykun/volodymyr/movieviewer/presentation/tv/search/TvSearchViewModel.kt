package com.shykun.volodymyr.movieviewer.presentation.tv.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.domain.TvUseCase

class TvSearchViewModel(private val tvUseCase: TvUseCase) : ViewModel() {

    private val searchedTvMutableLiveData = MutableLiveData<List<Tv>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val searchedLiveData: LiveData<List<Tv>> = searchedTvMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData


    fun searchTv(query: String) = tvUseCase.searchTv(query)
            .subscribe(
                    {response -> searchedTvMutableLiveData.value = response},
                    {error -> loadingErrorMutableLiveData.value = error.message}
            )
}