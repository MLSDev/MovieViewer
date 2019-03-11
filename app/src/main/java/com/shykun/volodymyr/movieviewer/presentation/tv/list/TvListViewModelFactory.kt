package com.shykun.volodymyr.movieviewer.presentation.tv.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.DiscoverUseCase
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import com.shykun.volodymyr.movieviewer.domain.TvUseCase
import javax.inject.Inject

class TvListViewModelFactory @Inject constructor(
        private val tvUseCase: TvUseCase,
        private val profileUseCase: ProfileUseCase,
        private val searchUseCase: SearchUseCase,
        private val discoverUseCase: DiscoverUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TvListViewModel(tvUseCase, profileUseCase, searchUseCase, discoverUseCase) as T
    }
}