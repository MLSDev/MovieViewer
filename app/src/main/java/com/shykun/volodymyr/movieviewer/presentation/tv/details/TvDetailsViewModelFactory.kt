package com.shykun.volodymyr.movieviewer.presentation.tv.details

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.TvDetailsUseCase
import javax.inject.Inject

class TvDetailsViewModelFactory @Inject constructor(private val tvDetailsUseCase: TvDetailsUseCase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TvDetailsViewModel(tvDetailsUseCase) as T
    }
}