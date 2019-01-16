package com.shykun.volodymyr.movieviewer.presentation.people.tab

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.GetPeopleUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class PeopleTabViewModelFactory @Inject constructor(
        private val getPeopleUseCase: GetPeopleUseCase,
        private val router: Router)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PeopleTabViewModel(getPeopleUseCase, router) as T
    }
}