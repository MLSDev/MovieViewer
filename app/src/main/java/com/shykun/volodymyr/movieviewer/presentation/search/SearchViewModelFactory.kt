package com.shykun.volodymyr.movieviewer.presentation.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(
        private val searchUseCase: SearchUseCase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(searchUseCase) as T
    }
}