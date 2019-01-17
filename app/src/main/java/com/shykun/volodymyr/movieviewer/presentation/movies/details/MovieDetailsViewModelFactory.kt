package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.GetMovieDetailsUseCase
import javax.inject.Inject

class MovieDetailsViewModelFactory @Inject constructor(
        private val getMovieDetailsUseCase: GetMovieDetailsUseCase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(getMovieDetailsUseCase) as T
    }
}