package com.shykun.volodymyr.movieviewer.presentation.movies.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase
import com.shykun.volodymyr.movieviewer.domain.ProfileUseCase
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import javax.inject.Inject

class MovieListViewModelFactory @Inject constructor(
        private val moviesUseCase: MoviesUseCase,
        private val profileUseCase: ProfileUseCase,
        private val searchUseCase: SearchUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieListViewModel(moviesUseCase, profileUseCase, searchUseCase) as T
    }
}