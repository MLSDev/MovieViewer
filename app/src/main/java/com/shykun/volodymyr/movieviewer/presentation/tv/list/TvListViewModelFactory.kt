package com.shykun.volodymyr.movieviewer.presentation.tv.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.GetTvUseCase
import javax.inject.Inject

class TvListViewModelFactory @Inject constructor(private val getTvUseCase: GetTvUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TvListViewModel(getTvUseCase) as T
    }
}