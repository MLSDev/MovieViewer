package com.shykun.volodymyr.movieviewer.presentation.discover

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.DiscoverUseCase
import javax.inject.Inject

class DiscoverViewModelFactory @Inject constructor(private val discoverUseCase: DiscoverUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DiscoverViewModel(discoverUseCase) as T
    }
}