package com.shykun.volodymyr.movieviewer.presentation.people.details

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shykun.volodymyr.movieviewer.domain.PersonDetailsUseCase
import javax.inject.Inject

class PersonDetailsViewModelFactory @Inject constructor(private val personDetailsUseCase: PersonDetailsUseCase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PersonDetailsViewModel(personDetailsUseCase) as T
    }
}