package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.GetTvUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class TvTabViewModelFactory @Inject constructor(
        private val getTVUseCase: GetTvUseCase,
        private val router: Router) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TvTabViewModel(getTVUseCase, router) as T
    }
}