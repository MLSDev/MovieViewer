package com.shykun.volodymyr.movieviewer.presentation.tv.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.TvUseCase
import javax.inject.Inject

class TvSearchViewModelFactory @Inject constructor(
        private val tvUseCase: TvUseCase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TvSearchViewModel(tvUseCase) as T
    }
}