package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MovieTabViewModelFactory @Inject constructor(
        private val moviesUseCase: MoviesUseCase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieTabViewModel(moviesUseCase) as T
    }
}