package com.shykun.volodymyr.movieviewer.presentation.tv.tab

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.TvUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TvTabViewModelFactory @Inject constructor(
        private val TVUseCase: TvUseCase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TvTabViewModel(TVUseCase, Schedulers.io(), AndroidSchedulers.mainThread()) as T
    }
}