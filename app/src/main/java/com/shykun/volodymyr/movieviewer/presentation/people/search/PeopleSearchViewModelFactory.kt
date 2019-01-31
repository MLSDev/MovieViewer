package com.shykun.volodymyr.movieviewer.presentation.people.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.PeopleUseCase
import javax.inject.Inject

class PeopleSearchViewModelFactory @Inject constructor(
        private val peopleUseCase: PeopleUseCase): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PeopleSearchViewModel(peopleUseCase) as T
    }
}