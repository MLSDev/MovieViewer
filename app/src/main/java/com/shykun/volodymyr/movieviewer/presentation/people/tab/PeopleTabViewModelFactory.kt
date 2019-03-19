package com.shykun.volodymyr.movieviewer.presentation.people.tab

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.PeopleUseCase
import com.shykun.volodymyr.movieviewer.domain.SearchUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PeopleTabViewModelFactory @Inject constructor(
        private val peopleUseCase: PeopleUseCase,
        private val searchUseCase: SearchUseCase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PeopleTabViewModel(
                peopleUseCase,
                searchUseCase,
                Schedulers.io(),
                AndroidSchedulers.mainThread()) as T
    }
}