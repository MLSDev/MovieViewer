package com.shykun.volodymyr.movieviewer.presentation.tv.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import com.shykun.volodymyr.movieviewer.domain.TvUseCase
import javax.inject.Inject

class TvListViewModelFactory @Inject constructor(
        private val tvUseCase: TvUseCase,
        private val profileUseCase: ProfileUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TvListViewModel(tvUseCase, profileUseCase) as T
    }
}