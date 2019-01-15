package com.shykun.volodymyr.movieviewer.presentation.people

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.GetPeopleUseCase
import javax.inject.Inject

class PeopleTabViewModelFactory @Inject constructor(private val getPeopleUseCase: GetPeopleUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PeopleTabViewModel(getPeopleUseCase) as T
    }
}