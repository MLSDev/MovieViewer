package com.shykun.volodymyr.movieviewer.presentation.movies.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase
import javax.inject.Inject

class MovieSearchViewModelFactory @Inject constructor(
        private val moviesUseCase: MoviesUseCase): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieSearchViewModel(moviesUseCase) as T
    }
}