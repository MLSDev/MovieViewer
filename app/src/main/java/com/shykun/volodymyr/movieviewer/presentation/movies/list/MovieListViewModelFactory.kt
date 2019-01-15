package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.GetMoviesUseCase
import javax.inject.Inject

class MovieListViewModelFactory @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase)
    : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieListViewModel(getMoviesUseCase) as T
    }
}