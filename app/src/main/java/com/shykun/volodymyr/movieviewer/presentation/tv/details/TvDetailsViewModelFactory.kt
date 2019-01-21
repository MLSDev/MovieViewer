package com.shykun.volodymyr.movieviewer.presentation.tv.details

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.GetTvDetailsUseCase
import javax.inject.Inject

class TvDetailsViewModelFactory @Inject constructor(private val getTvDetailsUseCase: GetTvDetailsUseCase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TvDetailsViewModel(getTvDetailsUseCase) as T
    }
}